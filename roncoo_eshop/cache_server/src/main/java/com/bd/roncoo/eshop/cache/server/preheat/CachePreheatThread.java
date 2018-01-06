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
 * 缓存预热线程
 */
public class CachePreheatThread extends Thread {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run() {
        CacheService cacheService = (CacheService) SpringContext.
                getApplicationContext().getBean("cacheService");
        ZooKeeperSession zkSession = ZooKeeperSession.getInstance();
        //获取Storm TaskId列表
        String taskIdList = zkSession.getNodeData("/taskId-list");
        logger.info("CachePreheatThread获取到taskIdList=" + taskIdList);
        if (taskIdList != null && !"".equals(taskIdList)) {
            String[] taskIdSplitList = taskIdList.split(",");
            for (String taskId : taskIdSplitList) {
                String taskIdLockPath = "/taskId-lock-" + taskId;
                boolean result = zkSession.acquireFastFailedDistributedLock(taskIdLockPath);
                if (!result) {
                    continue;
                }
                String taskIdStatusLockPath = "/taskId-status-lock-" + taskId;
                zkSession.acquireDistributedLock(taskIdStatusLockPath);
                String taskIdStatus = zkSession.getNodeData("/taskId-status-" + taskId);
                logger.info("CachePreheatThread获取task的预热状态 taskId=" + taskId + ", status=" + taskIdStatus);

                if ("".equals(taskIdStatus)) {
                    String productIdList = zkSession.getNodeData("/task-hot-product-list-" + taskId);
                    logger.info("CachePreheatThread获取到task的热门商品列表 productIdList=" + productIdList);
                    JSONArray productIdJSONArray = JSONArray.parseArray(productIdList);
                    for (int i = 0; i < productIdJSONArray.size(); i++) {
                        Long productId = productIdJSONArray.getLong(i);
                        String productInfoJSON = "{\"id\": " + productId +
                                ", \"name\": \"iPhone手机\", \"price\": 5599, \"pictureList\":\"a.jpg,b.jpg\", " +
                                "\"specification\": \"iPhone的规格\", \"service\": \"iPhone的售后服务\", " +
                                "\"color\": \"红色,白色,黑色\", \"size\": \"5.5\", \"shopId\": 1, " +
                                "\"modifiedTime\": \"2017-01-01 12:00:00\"}";
                        ProductInfo productInfo = JSONObject.parseObject(productInfoJSON, ProductInfo.class);
                        cacheService.saveProductInfo2LocalCache(productInfo);
                        logger.info("CachePreheatThread将商品数据设置到本地缓存中 productInfo=" + productInfo);
                        cacheService.saveProductInfo2RedisCache(productInfo);
                        logger.info("CachePreheatThread将商品数据设置到Redis缓存中 productInfo=" + productInfo);
                    }

                    zkSession.createNode("/taskId-status-" + taskId);
                    zkSession.setNodeData("/taskId-status-" + taskId, "success");
                }
                zkSession.releaseDistributedLock(taskIdStatusLockPath);
                zkSession.releaseDistributedLock(taskIdLockPath);
            }
        }
    }
}
