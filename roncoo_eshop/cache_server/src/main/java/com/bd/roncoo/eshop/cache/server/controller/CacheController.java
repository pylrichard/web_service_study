package com.bd.roncoo.eshop.cache.server.controller;

import com.bd.roncoo.eshop.cache.server.degrade.IsDegrade;
import com.bd.roncoo.eshop.cache.server.http.HttpClientUtils;
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

@RestController
public class CacheController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CacheService cacheService;

    @GetMapping("/change/product")
    public String changeProduct(Long productId) {
        String url = "http://localhost:8082/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        logger.info(response);

        return "success";
    }

    /**
     * 各级缓存失效，Nginx发送请求到缓存服务获取原始数据
     */
    @GetMapping("/getProductInfo")
    public ProductInfo getProductInfo(Long productId) {
        HystrixCommand<ProductInfo> getProductInfoCommand = new GetProductInfoCommand(productId);
        ProductInfo productInfo = getProductInfoCommand.execute();
        Long cityId = productInfo.getCityId();
        GetCityNameCommand getCityNameCommand = new GetCityNameCommand(cityId);
        String cityName = getCityNameCommand.execute();
        productInfo.setCityName(cityName);
        Long brandId = productInfo.getBrandId();
        GetBrandNameCommand getBrandNameCommand = new GetBrandNameCommand(brandId);
        String brandName = getBrandNameCommand.execute();
        productInfo.setBrandName(brandName);
        Future<ProductInfo> future = getProductInfoCommand.queue();
        try {
            Thread.sleep(1000);
            logger.info("商品信息:" + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productInfo;
    }

    @GetMapping("/getProductInfoFromCache")
    public ProductInfo getProductInfoFromCache(Long productId) {
        ProductInfo productInfo = null;
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
            GetProductInfoCommand command = new GetProductInfoCommand(productId);
            productInfo = command.execute();
            /*
				将数据推送到内存队列中
			 */
            RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
            rebuildCacheQueue.putProductInfo(productInfo);
        }

        return productInfo;
    }

    /**
     * 批量查询多条商品数据的请求
     */
    @GetMapping("/getProductInfos")
    public String getProductInfos(String productIds) {
        HystrixObservableCommand<ProductInfo> getProductInfosCommand =
                new GetProductInfosCommand(productIds.split(","));
        Observable<ProductInfo> observable = getProductInfosCommand.observe();
        //还没有执行
        observable = getProductInfosCommand.toObservable();
        //调用subscribe()后才会执行
        observable.subscribe(new Observer<ProductInfo>() {
            @Override
            public void onCompleted() {
                logger.info("获取完了所有的商品数据");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(ProductInfo productInfo) {
                logger.info("商品信息:" + productInfo);
            }
        });
        for (String productId : productIds.split(",")) {
            GetProductInfoCommand getProductInfoCommand = new GetProductInfoCommand(
                    Long.valueOf(productId));
            ProductInfo productInfo = getProductInfoCommand.execute();
            logger.info("商品信息:" + productInfo);
            String result = getProductInfoCommand.isResponseFromCache() ? "是" : "否";
            logger.info("是否来自缓存:" + result);
        }
        List<Future<ProductInfo>> futures = new ArrayList<>();
        for (String productId : productIds.split(",")) {
            GetProductInfosCollapse getProductInfosCollapse =
                    new GetProductInfosCollapse(Long.valueOf(productId));
            futures.add(getProductInfosCollapse.queue());
        }
        try {
            for (Future<ProductInfo> future : futures) {
                logger.info("CacheController的结果:" + future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

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
