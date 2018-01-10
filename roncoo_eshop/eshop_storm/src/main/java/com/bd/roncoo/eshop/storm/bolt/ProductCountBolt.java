package com.bd.roncoo.eshop.storm.bolt;

import com.bd.roncoo.eshop.common.http.HttpClientUtils;
import com.bd.roncoo.eshop.common.zk.ZooKeeperSession;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.storm.shade.org.json.simple.JSONArray;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.trident.util.LRUMap;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品访问次数统计Bolt，基于LRUMap完成统计
 * <p>
 * 1 Storm Task启动时基于ZK分布式锁将TaskId累加到一个ZNode中
 * 2 创建1个独立的后台线程，每隔一段时间(Utils.sleep可配置)计算得到TopN热门商品列表
 * 3 每个Storm Task将TopN热门商品列表写入对应的ZNode中
 */
public class ProductCountBolt extends BaseRichBolt {
    private static final long serialVersionUID = -8761807561458126413L;
    private static final Logger logger = LoggerFactory.getLogger(ProductCountBolt.class);
    /**
     * key为商品id，value为访问次数
     */
    private LRUMap<Long, Long> productCountMap = new LRUMap<>(1000);
    private ZooKeeperSession zkSession;
    private int taskId;

    @Override
    @SuppressWarnings("rawtypes")
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.zkSession = ZooKeeperSession.getInstance();
        this.taskId = context.getThisTaskId();
        new Thread(new ProductCountThread()).start();
        new Thread(new HotProductFindThread()).start();
        /*
            1 将TaskId写入一个ZNode中生成TaskId列表
            2 每次将热门商品列表写入TaskId对应的ZNode
            3 并行执行的缓存预热线程才能获取第1步生成的TaskId列表
            4 缓存预热线程根据每个TaskId获取一个锁，然后从对应的ZNode中获取到热门商品列表
        */
        initTaskId(this.taskId);
    }

    private void initTaskId(int taskId) {
        zkSession.acquireDistributedLock("/taskId-list-lock");
        //创建ZNode
        zkSession.createNode("/taskId-list");
        String taskIdList = zkSession.getNodeData("/taskId-list");
        logger.info("ProductCountBolt获取到taskIdList=" + taskIdList);
        //用逗号分隔拼接成一个列表
        if (!"".equals(taskIdList)) {
            taskIdList += "," + taskId;
        } else {
            taskIdList += taskId;
        }
        //ProductCountBolt所有Task启动时会将TaskId写到同一个ZNode中
        zkSession.setNodeData("/taskId-list", taskIdList);
        logger.info("ProductCountBolt设置taskIdList=" + taskIdList);
        zkSession.releaseDistributedLock("/taskId-list-lock");
    }

    private class HotProductFindThread implements Runnable {
        @Override
        @SuppressWarnings("deprecation")
        public void run() {
            List<Map.Entry<Long, Long>> productCountList = new ArrayList<>();
            List<Long> hotProductIdList = new ArrayList<>();
            List<Long> lastTimeHotProductIdList = new ArrayList<>();
            while (true) {
                /*
                    1 将LRUMap中的数据按照访问次数进行全局排序
                    2 计算排名后95%商品的访问次数平均值
                    3 遍历排序后的商品访问次数，从最大开始
                    4 如果某个商品访问量是平均值的10倍，认为是热点缓存
                */
                try {
                    productCountList.clear();
                    hotProductIdList.clear();
                    if (productCountMap.size() == 0) {
                        Utils.sleep(100);
                        continue;
                    }
                    logger.info("HotProductFindThread打印productCountMap的长度 size=" + productCountMap.size());
                    /*
                        1 全局排序
                     */
                    for (Map.Entry<Long, Long> productCountEntry : productCountMap.entrySet()) {
                        if (productCountList.size() == 0) {
                            productCountList.add(productCountEntry);
                        } else {
                            boolean bigger = false;
                            for (int i = 0; i < productCountList.size(); i++) {
                                Map.Entry<Long, Long> countEntry = productCountList.get(i);
                                if (productCountEntry.getValue() > countEntry.getValue()) {
                                    int lastIndex = productCountList.size() < productCountMap.size()
                                            ? productCountList.size() - 1 : productCountMap.size() - 2;
                                    for (int j = lastIndex; j >= i; j--) {
                                        if (j + 1 == productCountList.size()) {
                                            productCountList.add(null);
                                        }
                                        productCountList.set(j + 1, productCountList.get(j));
                                    }
                                    productCountList.set(i, productCountEntry);
                                    bigger = true;
                                    break;
                                }
                            }
                            if (!bigger) {
                                if (productCountList.size() < productCountMap.size()) {
                                    productCountList.add(productCountEntry);
                                }
                            }
                        }
                    }
                    logger.info("HotProductFindThread全局排序后的结果 productCountList=" + productCountList);
                    /*
                        2 计算95%商品的访问次数平均值
                     */
                    int calculateCount = (int) Math.floor(productCountList.size() * 0.95);
                    Long totalCount = 0L;
                    for (int i = productCountList.size() - 1; i >= productCountList.size() - calculateCount; i--) {
                        totalCount += productCountList.get(i).getValue();
                    }
                    Long avgCount = totalCount / calculateCount;
                    logger.info("HotProductFindThread计算出排名后95%商品的访问次数平均值 avgCount=" + avgCount);
                    /*
                        3 从第一个元素开始遍历，判断是否是平均值的10倍
                     */
                    for (Map.Entry<Long, Long> productCountEntry : productCountList) {
                        if (productCountEntry.getValue() > 10 * avgCount) {
                            logger.info("HotProductFindThread发现一个热点 productCountEntry=" + productCountEntry);
                            hotProductIdList.add(productCountEntry.getKey());
                            /*
                                只会反向推送新的热点缓存
                             */
                            if (!lastTimeHotProductIdList.contains(productCountEntry.getKey())) {
                                /*
                                    将热点商品id反向推送到流量分发的Nginx
                                    见distribute_hot_product_cache.lua
                                 */
                                String distributeNginxURL = "http://192.168.8.10/hot?productId=" + productCountEntry.getKey();
                                HttpClientUtils.sendGetRequest(distributeNginxURL);
                                /*
                                    从缓存服务获取热点商品的缓存数据，反向推送到所有的应用Nginx
                                    见app_hot_product_cache.lua
                                 */
                                String cacheServiceURL = "http://192.168.8.11:8080/getProductInfo?productId=" + productCountEntry.getKey();
                                String response = HttpClientUtils.sendGetRequest(cacheServiceURL);
                                List<NameValuePair> params = new ArrayList<>();
                                params.add(new BasicNameValuePair("productInfo", response));
                                String productInfo = URLEncodedUtils.format(params, HTTP.UTF_8);
                                String[] appNginxURLs = new String[]{
                                        "http://192.168.8.10/hot?productId=" + productCountEntry.getKey() + "&" + productInfo,
                                        "http://192.168.8.11/hot?productId=" + productCountEntry.getKey() + "&" + productInfo
                                };
                                for (String appNginxURL : appNginxURLs) {
                                    HttpClientUtils.sendGetRequest(appNginxURL);
                                }
                            }
                        }
                    }
                    /*
                        4 实时感知失效的热点数据
                     */
                    if (lastTimeHotProductIdList.size() == 0) {
                        if (hotProductIdList.size() > 0) {
                            for (Long productId : hotProductIdList) {
                                lastTimeHotProductIdList.add(productId);
                            }
                            logger.info("HotProductFindThread保存上次热点数据 lastTimeHotProductIdList=" + lastTimeHotProductIdList);
                        }
                    } else {
                        for (Long productId : lastTimeHotProductIdList) {
                            if (!hotProductIdList.contains(productId)) {
                                logger.info("HotProductFindThread发现一个热点失效 productId=" + productId);
                                //发送请求到流量分发Nginx，取消热点缓存标识，见
                                String url = "http://192.168.8.10/cancel_hot?productId=" + productId;
                                HttpClientUtils.sendGetRequest(url);
                            }
                        }
                        if (hotProductIdList.size() > 0) {
                            lastTimeHotProductIdList.clear();
                            for (Long productId : hotProductIdList) {
                                lastTimeHotProductIdList.add(productId);
                            }
                            logger.info("HotProductFindThread保存上次热点数据 lastTimeHotProductIdList=" + lastTimeHotProductIdList);
                        } else {
                            lastTimeHotProductIdList.clear();
                        }
                    }
                    Utils.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ProductCountThread implements Runnable {
        @Override
        public void run() {
            //Map.Entry中key为商品id，value为访问次数
            List<Map.Entry<Long, Long>> topNProductList = new ArrayList<>();
            List<Long> productIdList = new ArrayList<>();
            //无限循环中的代码位于try...catch中方便捕获异常
            while (true) {
                try {
                    topNProductList.clear();
                    productIdList.clear();
                    int topN = 3;
                    if (productCountMap.size() == 0) {
                        Utils.sleep(100);
                        continue;
                    }
                    logger.info("ProductCountThread中productCountMap size=" + productCountMap.size());
                    /*
                        遍历productCountMap
                        比较productCountMap中商品访问次数和topNProductList中商品访问次数的大小
                        构建TopN热门商品列表数据
                     */
                    for (Map.Entry<Long, Long> productCountEntry : productCountMap.entrySet()) {
                        if (topNProductList.size() == 0) {
                            topNProductList.add(productCountEntry);
                        } else {
                            boolean bigger = false;
                            for (int i = 0; i < topNProductList.size(); i++) {
                                Map.Entry<Long, Long> topNProductCountEntry = topNProductList.get(i);
                                //如果productCountMap中一个商品访问次数比topNProductList中一个商品访问次数大
                                if (productCountEntry.getValue() > topNProductCountEntry.getValue()) {
                                    /*
                                        将i下标之后(包含i)的元素向后移动1个位置
                                     */
                                    int lastIndex = topNProductList.size() < topN ? topNProductList.size() - 1 : topN - 2;
                                    for (int j = lastIndex; j >= i; j--) {
                                        //边界处理
                                        if (j + 1 == topNProductList.size()) {
                                            topNProductList.add(null);
                                        }
                                        topNProductList.set(j + 1, topNProductList.get(j));
                                    }
                                    //复制productCountMap中对应的商品数据到topNProductList中i下标所在位置
                                    topNProductList.set(i, productCountEntry);
                                    bigger = true;
                                    break;
                                }
                            }
                            /*
                                如果productCountMap中一个商品访问次数比topNProductList中一个商品访问次数小
                             */
                            if (!bigger) {
                                if (topNProductList.size() < topN) {
                                    //添加productCountMap中对应的商品数据到topNProductList尾部
                                    topNProductList.add(productCountEntry);
                                }
                            }
                        }
                    }
                    /*
                        将TopN热门商品列表的商品id列表写入TaskId对应的ZNode
                     */
                    for (Map.Entry<Long, Long> topNProductEntry : topNProductList) {
                        productIdList.add(topNProductEntry.getKey());
                    }
                    String topNProductListJSON = JSONArray.toJSONString(productIdList);
                    //在缓存预热线程中使用
                    String path = "/task-hot-product-list-" + taskId;
                    zkSession.createNode(path);
                    zkSession.setNodeData(path, topNProductListJSON);
                    logger.info("ProductCountThread计算得到TopN热门商品列表 ZK path=" + path
                            + ", topNProductListJSON=" + topNProductListJSON);
                    //每隔5s计算一次TopN热门商品列表
                    Utils.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void execute(Tuple tuple) {
        Long productId = tuple.getLongByField("productId");
        logger.info("ProductCountBolt接收到一个商品id  productId=" + productId);
        Long count = productCountMap.get(productId);
        if (count == null) {
            count = 0L;
        }
        count++;
        productCountMap.put(productId, count);
        logger.info("ProductCountBolt完成商品访问次数统计 productId=" + productId + ", count=" + count);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }
}
