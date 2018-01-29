package com.bd.roncoo.eshop.cache.server.preheat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.bd.roncoo.eshop.cache.server.service.CacheService;
import com.bd.roncoo.eshop.cache.server.spring.SpringContext;
import com.bd.roncoo.eshop.common.zk.ZooKeeperSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务启动时进行缓存预热，通过双重ZK分布式锁保证缓存只被缓存数据服务预热一次
 */
public class CachePreheatThread extends Thread {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run() {
        CacheService cacheService = (CacheService) SpringContext.
                getApplicationContext().getBean("cacheService");
        ZooKeeperSession zkSession = ZooKeeperSession.getInstance();
        //获取在ProductCountBolt.initTaskId()中创建的Storm TaskId列表
        String taskIdList = zkSession.getNodeData("/taskId-list");
        logger.info("CachePreheatThread获取到taskIdList=" + taskIdList);
        if (taskIdList != null && !"".equals(taskIdList)) {
            String[] taskIdSplitList = taskIdList.split(",");
            /*
                遍历每个TaskId，尝试获取分布式锁，如果获取不到就报错，不要等待，说明已经有其它服务实例在进行缓存预热
             */
            for (String taskId : taskIdSplitList) {
                String taskIdLockPath = "/taskId-lock-" + taskId;
                //获取第1重分布式锁
                boolean result = zkSession.acquireFastFailedDistributedLock(taskIdLockPath);
                if (!result) {
                    continue;
                }
                String taskIdStatusLockPath = "/taskId-status-lock-" + taskId;
                //获取第2重分布式锁
                zkSession.acquireDistributedLock(taskIdStatusLockPath);
                /*
                    获取到分布式锁后检查TaskId的预热状态，如果已经预热过了，不需要再次预热
                 */
                String taskIdStatus = zkSession.getNodeData("/taskId-status-" + taskId);
                logger.info("CachePreheatThread获取task的预热状态 taskId=" + taskId + ", status=" + taskIdStatus);
                if ("".equals(taskIdStatus)) {
                    //ProductCountThread.run()中写入
                    String productIdList = zkSession.getNodeData("/task-hot-product-list-" + taskId);
                    logger.info("CachePreheatThread获取到task的热门商品列表 productIdList=" + productIdList);
                    JSONArray productIdJSONArray = JSONArray.parseArray(productIdList);
                    /*
                        执行预热操作，遍历productId列表
                    */
                    for (int i = 0; i < productIdJSONArray.size(); i++) {
                        Long productId = productIdJSONArray.getLong(i);
                        /*
                            TODO 从商品服务查询商品数据
                         */
                        String productInfoJSON = "{\"id\": " + productId +
                                ", \"name\": \"iPhone手机\", \"price\": 5599, \"pictureList\":\"a.jpg,b.jpg\", " +
                                "\"specification\": \"iPhone的规格\", \"service\": \"iPhone的售后服务\", " +
                                "\"color\": \"红色,白色,黑色\", \"size\": \"5.5\", \"shopId\": 1, " +
                                "\"modifiedTime\": \"2017-01-01 12:00:00\"}";
                        ProductInfo productInfo = JSONObject.parseObject(productInfoJSON, ProductInfo.class);
                        /*
                            写入商品数据到EhCache和Redis主集群
                         */
                        cacheService.saveProductInfo2LocalCache(productInfo);
                        logger.info("CachePreheatThread将商品数据设置到本地缓存中 productInfo=" + productInfo);
                        cacheService.saveProductInfo2RedisCache(productInfo);
                        logger.info("CachePreheatThread将商品数据设置到Redis缓存中 productInfo=" + productInfo);
                    }
                    zkSession.createNode("/taskId-status-" + taskId);
                    //设置TaskId对应的预热状态为成功，不需要再次预热
                    zkSession.setNodeData("/taskId-status-" + taskId, "success");
                }
                zkSession.releaseDistributedLock(taskIdStatusLockPath);
                zkSession.releaseDistributedLock(taskIdLockPath);
            }
        }
    }
}
