package com.bd.roncoo.eshop.cache.server.rebuild;

import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.bd.roncoo.eshop.cache.server.service.CacheService;
import com.bd.roncoo.eshop.cache.server.spring.SpringContext;
import com.bd.roncoo.eshop.common.zk.ZooKeeperSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 缓存重建线程
 */
public class RebuildCacheThread implements Runnable {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run() {
        RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
        ZooKeeperSession zkSession = ZooKeeperSession.getInstance();
        CacheService cacheService = (CacheService) SpringContext.getApplicationContext().getBean("cacheService");
        while (true) {
            ProductInfo productInfo = rebuildCacheQueue.takeProductInfo();
            zkSession.acquireDistributedLock(productInfo.getId());
            ProductInfo existedProductInfo = cacheService.getProductInfoFromRedisCache(productInfo.getId());
            if (existedProductInfo != null) {
                //比较当前数据的时间和已有数据的时间
                try {
                    Date date = sdf.parse(productInfo.getModifiedTime());
                    Date existedDate = sdf.parse(existedProductInfo.getModifiedTime());
                    if (date.before(existedDate)) {
                        logger.info("current date[" + productInfo.getModifiedTime()
                                + "] is before existed date[" + existedProductInfo.getModifiedTime() + "]");
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("current date[" + productInfo.getModifiedTime()
                        + "] is after existed date[" + existedProductInfo.getModifiedTime() + "]");
            } else {
                logger.info("existed product info is null......");
            }
            cacheService.saveProductInfo2LocalCache(productInfo);
            cacheService.saveProductInfo2RedisCache(productInfo);
            zkSession.releaseDistributedLock(productInfo.getId());
        }
    }
}
