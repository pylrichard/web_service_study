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
 * 第一次访问时在Nginx本地缓存中获取不到数据，所以会发送http请求到缓存数据服务从Redis获取
 * 获取到数据后，存储到Nginx本地缓存，过期时间为10分钟
 * 然后将所有数据渲染到模板中，之后的访问请求从Nginx本地缓存获取数据
 * 缓存数据服务发现有数据变更->主动更新2级缓存(EhCache+Redis)->缓存维度化拆分
 * 分发层Nginx+应用层Nginx->自定义流量分发策略提高缓存命中率
 * Nginx shared dict缓存->缓存数据服务->Redis->EhCache->渲染html模板->返回html
 * 如果数据在Nginx本地缓存->Redis->EhCache这3级缓存中都获取不到数据，可能是被LRU清理掉了
 * 这时缓存数据服务重新获取数据进行分布式重建缓存，更新到EhCache和Redis
 *
 * 分布式重建缓存的并发冲突问题见57-分布式缓存重建并发冲突问题以及Zookeeper分布式锁解决方案
 *
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
                //比较当前数据的更新时间和已有数据的更新时间
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
