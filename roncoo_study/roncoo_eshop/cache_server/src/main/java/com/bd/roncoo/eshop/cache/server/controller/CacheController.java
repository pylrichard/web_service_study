package com.bd.roncoo.eshop.cache.server.controller;

import com.bd.roncoo.eshop.cache.server.degrade.IsDegrade;
import com.bd.roncoo.eshop.cache.server.hystrix.command.*;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.bd.roncoo.eshop.cache.server.model.ShopInfo;
import com.bd.roncoo.eshop.cache.server.preheat.CachePreheatThread;
import com.bd.roncoo.eshop.cache.server.rebuild.RebuildCacheQueue;
import com.bd.roncoo.eshop.cache.server.service.CacheService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 运行在Tomcat的线程中
 */
@RestController
public class CacheController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CacheService cacheService;

    /**
     * Nginx本地缓存+Redis从集群缓存都失效，Nginx的Lua脚本会发送请求到缓存数据服务获取数据
     * 见app_cache_ha.lua
     *
     * 对比DataLinkController.getProduct()
     */
    @GetMapping("/getProductInfo")
    public ProductInfo getProductInfo(Long productId) {
        ProductInfo productInfo = null;
        /*
            缓存数据服务可以访问/主动更新Redis主集群，但不访问Redis从集群
         */
        productInfo = cacheService.getProductInfoFromRedisCache(productId);
        if (productInfo != null) {
            logger.info("从Redis中获取缓存，商品信息=" + productInfo);
        }
        if (productInfo == null) {
            productInfo = cacheService.getProductInfoFromLocalCache(productId);
            if (productInfo != null) {
                logger.info("从EhCache中获取缓存，商品信息=" + productInfo);
            }
        }
        if (productInfo == null) {
            /*
                在多级缓存都没有获取到数据，就调用服务API获取数据返回给Nginx
             */
            //线程池隔离
            HystrixCommand<ProductInfo> getProductInfoCommand = new GetProductInfoCommand(productId);
            //同步调用
            productInfo = getProductInfoCommand.execute();
            //信号量隔离
            Long cityId = productInfo.getCityId();
            GetCityNameCommand getCityNameCommand = new GetCityNameCommand(cityId);
            String cityName = getCityNameCommand.execute();
            productInfo.setCityName(cityName);
            Long brandId = productInfo.getBrandId();
            GetBrandNameCommand getBrandNameCommand = new GetBrandNameCommand(brandId);
            String brandName = getBrandNameCommand.execute();
            productInfo.setBrandName(brandName);
            /*
                异步调用
                queue()将command放入线程池的一个等待队列，返回一个Future对象
                过一段时间对Future调用get()获取数据
             */
            Future<ProductInfo> future = getProductInfoCommand.queue();
            try {
                Thread.sleep(1000);
                logger.info("商品信息:" + future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*
                被动重建
                推送数据到重建内存队列，缓存重建线程异步执行重建
                缓存重建线程先获取分布式锁，然后比较更新时间，判断是否需要更新Redis主集群
                见57-分布式缓存重建并发冲突问题以及Zookeeper分布式锁解决方案
			 */
            RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
            rebuildCacheQueue.putProductInfo(productInfo);
        }

        return productInfo;
    }

    /**
     * 批量查询多条商品数据
     */
    @GetMapping("/getProductsInfo")
    public String getProductsInfo(String productIds) {
        HystrixObservableCommand<ProductInfo> getProductsInfoCommand =
                new GetProductsInfoCommand(productIds.split(","));
        //立即执行command
        Observable<ProductInfo> observable = getProductsInfoCommand.observe();
        /*
            不会立即执行command，调用subscribe()后才会执行
         */
        observable = getProductsInfoCommand.toObservable();
        observable.subscribe(new Observer<ProductInfo>() {
            @Override
            public void onCompleted() {
                logger.info("获取完了所有的商品数据");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            /**
             * 见GetProductsInfoCommand.construct()中的observer.onNext(productInfo)
             */
            @Override
            public void onNext(ProductInfo productInfo) {
                logger.info("商品信息:" + productInfo);
            }
        });
        /*
            见94-基于request cache请求缓存技术优化批量商品数据查询接口
         */
        for (String productId : productIds.split(",")) {
            GetProductInfoCommand getProductInfoCommand = new GetProductInfoCommand(
                    Long.valueOf(productId));
            ProductInfo productInfo = getProductInfoCommand.execute();
            logger.info("商品信息:" + productInfo);
            //判断返回结果是否来自缓存
            String result = getProductInfoCommand.isResponseFromCache() ? "是" : "否";
            logger.info("是否来自缓存:" + result);
        }
        /*
            见100-基于request collapser请求合并技术进一步优化批量查询
         */
        List<Future<ProductInfo>> futures = new ArrayList<>();
        for (String productId : productIds.split(",")) {
            GetProductsInfoCollapser getProductsInfoCollapser = new GetProductsInfoCollapser(
                    Long.valueOf(productId));
            futures.add(getProductsInfoCollapser.queue());
        }
        try {
            for (Future<ProductInfo> future : futures) {
                logger.info("GetProductsInfoCollapser的结果:" + future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

    /**
     * 设置手动降级标识
     */
    @GetMapping("/isDegrade")
    public String isDegrade(boolean degrade) {
        IsDegrade.setDegrade(degrade);

        return "success";
    }

    @GetMapping("/putCache")
    public String putCache(ProductInfo productInfo) {
        cacheService.saveLocalCache(productInfo);

        return "success";
    }

    @GetMapping("/getCache")
    public ProductInfo getCache(Long id) {
        return cacheService.getLocalCache(id);
    }

    @GetMapping("/getShopInfo")
    public ShopInfo getShopInfo(Long shopId) {
        ShopInfo shopInfo = null;
        shopInfo = cacheService.getShopInfoFromRedisCache(shopId);
        logger.info("从Redis中获取缓存，店铺信息=" + shopInfo);
        if (shopInfo == null) {
            shopInfo = cacheService.getShopInfoFromLocalCache(shopId);
            logger.info("从EhCache中获取缓存，店铺信息=" + shopInfo);
        }
        if (shopInfo == null) {
			/*
				TODO 从数据源重新获取数据，重建缓存
			 */
        }
        return shopInfo;
    }

    @GetMapping("/preheatCache")
    public void preheatCache() {
        new CachePreheatThread().start();
    }
}
