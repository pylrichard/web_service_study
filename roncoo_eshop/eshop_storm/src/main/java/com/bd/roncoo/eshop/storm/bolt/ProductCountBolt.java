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
 * 商品访问次数统计Bolt
 */
public class ProductCountBolt extends BaseRichBolt {
    private static final long serialVersionUID = -8761807561458126413L;
    private static final Logger logger = LoggerFactory.getLogger(ProductCountBolt.class);
    private LRUMap<Long, Long> productCountMap = new LRUMap<Long, Long>(1000);
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
            1 将自己的taskId写入一个Zookeeper Node中，形成taskId列表
            2 每次将自己的热门商品列表，写入自己的taskId对应的Zookeeper节点
            3 并行执行的缓存预热线程才能从第一步中知道有哪些taskId
            4 缓存预热线程根据每个taskId获取一个锁，然后从对应的ZNode中获取到热门商品列表
        */
        initTaskId(context.getThisTaskId());
    }

    private void initTaskId(int taskId) {
        //ProductCountBolt所有的Task启动时会将自己的taskId写到同一个ZNode中，用逗号分隔拼接成一个列表
        zkSession.acquireDistributedLock("/taskId-list-lock");
        zkSession.createNode("/taskId-list");
        String taskIdList = zkSession.getNodeData("/taskId-list");
        logger.info("ProductCountBolt获取到taskIdList=" + taskIdList);
        if (!"".equals(taskIdList)) {
            taskIdList += "," + taskId;
        } else {
            taskIdList += taskId;
        }
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
                    2 计算95%商品的访问次数平均值
                    3 遍历排序后的商品访问次数，从最大开始
                    4 如果某个商品访问量是平均值的10倍，认为是缓存热点
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
                            //比较大小，得到最热topN
                            boolean bigger = false;
                            for (int i = 0; i < productCountList.size(); i++) {
                                Map.Entry<Long, Long> topNProductCountEntry = productCountList.get(i);
                                if (productCountEntry.getValue() > topNProductCountEntry.getValue()) {
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
                    logger.info("HotProductFindThread计算出95%商品的访问次数平均值 avgCount=" + avgCount);
                    /*
                        3 从第一个元素开始遍历，判断是否是平均值的10倍
                     */
                    for (Map.Entry<Long, Long> productCountEntry : productCountList) {
                        if (productCountEntry.getValue() > 10 * avgCount) {
                            logger.info("HotProductFindThread发现一个热点 productCountEntry=" + productCountEntry);
                            hotProductIdList.add(productCountEntry.getKey());
                            if (!lastTimeHotProductIdList.contains(productCountEntry.getKey())) {
                                //将缓存热点反向推送到流量分发的Nginx
                                String distributeNginxURL = "http://192.168.8.10/hot?productId=" + productCountEntry.getKey();
                                HttpClientUtils.sendGetRequest(distributeNginxURL);
                                //从缓存服务获取热点商品的缓存数据，反向推送到所有的应用Nginx
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
                                //发送请求到流量分发Nginx，取消热点缓存标识
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
            List<Map.Entry<Long, Long>> topNProductList = new ArrayList<>();
            List<Long> productIdList = new ArrayList<>();
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
                    for (Map.Entry<Long, Long> productCountEntry : productCountMap.entrySet()) {
                        if (topNProductList.size() == 0) {
                            topNProductList.add(productCountEntry);
                        } else {
                            boolean bigger = false;
                            for (int i = 0; i < topNProductList.size(); i++) {
                                Map.Entry<Long, Long> topNProductCountEntry = topNProductList.get(i);

                                if (productCountEntry.getValue() > topNProductCountEntry.getValue()) {
                                    int lastIndex = topNProductList.size() < topN ? topNProductList.size() - 1 : topN - 2;
                                    for (int j = lastIndex; j >= i; j--) {
                                        if (j + 1 == topNProductList.size()) {
                                            topNProductList.add(null);
                                        }
                                        topNProductList.set(j + 1, topNProductList.get(j));
                                    }
                                    topNProductList.set(i, productCountEntry);
                                    bigger = true;
                                    break;
                                }
                            }
                            if (!bigger) {
                                if (topNProductList.size() < topN) {
                                    topNProductList.add(productCountEntry);
                                }
                            }
                        }
                    }
                    //获取一个topN列表
                    for (Map.Entry<Long, Long> topNProductEntry : topNProductList) {
                        productIdList.add(topNProductEntry.getKey());
                    }
                    String topNProductListJSON = JSONArray.toJSONString(productIdList);
                    zkSession.createNode("/task-hot-product-list-" + taskId);
                    zkSession.setNodeData("/task-hot-product-list-" + taskId, topNProductListJSON);
                    logger.info("ProductCountThread计算得到Top3热门商品列表 ZK path="
                            + ("/task-hot-product-list-" + taskId) + ", topNProductListJSON=" + topNProductListJSON);
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
