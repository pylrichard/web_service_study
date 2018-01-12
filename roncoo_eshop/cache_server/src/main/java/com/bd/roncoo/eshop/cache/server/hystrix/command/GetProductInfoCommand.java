package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.cache.server.cache.local.BrandCache;
import com.bd.roncoo.eshop.cache.server.cache.local.LocationCache;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.bd.roncoo.eshop.common.http.HttpClientUtils;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hystrix中最基本的资源隔离就是线程池隔离
 * HystrixCommand封装服务调用请求，使用线程池内的一个线程来处理请求
 * 如果此时并发调用请求数有1000，但是线程池内只有10个线程，只会使用这10个线程处理请求
 * 不会因为依赖服务调用延迟，将Tomcat内所有的线程耗尽，导致缓存数据服务不可用
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {
    public static final HystrixCommandKey KEY = HystrixCommandKey.Factory.asKey("GetProductInfoCommand");
    private Long productId;

    public GetProductInfoCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductService"))
                .andCommandKey(KEY)
                /*
                    设置线程池参数
                 */
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductInfoPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(3)
                        .withMaximumSize(30)
                        .withAllowMaximumSizeToDivergeFromCoreSize(true)
                        .withKeepAliveTimeMinutes(1)
                        .withMaxQueueSize(12)
                        .withQueueSizeRejectionThreshold(15))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(30)
                        .withCircuitBreakerErrorThresholdPercentage(40)
                        .withCircuitBreakerSleepWindowInMilliseconds(3000)
                        .withExecutionTimeoutInMilliseconds(500)
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(30))
        );
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        if (productId.equals(-1L) || productId.equals(-2L)) {
            throw new Exception();
        }
        if (productId == 100) {
            //生产环境如果没有查询到数据，返回一个商品信息
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(productId);

            return productInfo;
        } else {
            //发送请求调用商品服务API
            String url = "http://localhost:8082/getProductInfo?productId=" + productId;
            String response = HttpClientUtils.sendGetRequest(url);

            return JSONObject.parseObject(response, ProductInfo.class);
        }
    }

    public static void flushCache(Long productId) {
        HystrixRequestCache.getInstance(KEY,
                HystrixConcurrencyStrategyDefault.getInstance()).clear("product_info_" + productId);
    }

    @Override
    protected String getCacheKey() {
        return "product_info_" + productId;
    }

    @Override
    protected ProductInfo getFallback() {
        //也可以调用new HBaseColdDataCommand(productId).execute()
        return new FirstLevelFallbackCommand(productId).execute();
    }

    private static class FirstLevelFallbackCommand extends HystrixCommand<ProductInfo> {
        private Long productId;

        public FirstLevelFallbackCommand(Long productId) {
            /*
				第一级降级策略中command运行在fallback中
				做多级降级时创建独立的降级command线程池
				如果主流程的command都失效，线程池可能已被占满
				所以降级command必须使用独立线程池
			*/
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductService"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("FirstLevelFallbackCommand"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("FirstLevelFallbackPool"))
            );

            this.productId = productId;
        }

        @Override
        protected ProductInfo run() throws Exception {
            //第一级降级策略从备用机房的机器调用接口
            if (productId.equals(-2L)) {
                throw new Exception();
            }
            String url = "http://localhost:8082/getProductInfo?productId=" + productId;
            String response = HttpClientUtils.sendGetRequest(url);

            return JSONObject.parseObject(response, ProductInfo.class);
        }

        @Override
        protected ProductInfo getFallback() {
            //第二级降级策略和第一级降级策略都失效
            ProductInfo productInfo = new ProductInfo();
            //从请求参数中获取数据
            productInfo.setId(productId);
            //从本地缓存中获取数据
            productInfo.setBrandId(BrandCache.getBrandId(productId));
            productInfo.setBrandName(BrandCache.getBrandName(productInfo.getBrandId()));
            productInfo.setCityId(LocationCache.getCityId(productId));
            productInfo.setCityName(LocationCache.getCityName(productInfo.getCityId()));
            //填充默认数据
            productInfo.setColor("默认颜色");
            productInfo.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            productInfo.setName("默认商品");
            productInfo.setPictureList("default.jpg");
            productInfo.setPrice(0.0);
            productInfo.setService("默认售后服务");
            productInfo.setShopId(-1L);
            productInfo.setSize("默认大小");
            productInfo.setSpecification("默认规格");

            return productInfo;
        }
    }

    private class HBaseColdDataCommand extends HystrixCommand<ProductInfo> {
        private Long productId;

        public HBaseColdDataCommand(Long productId) {
            super(HystrixCommandGroupKey.Factory.asKey("HBaseGroup"));
            this.productId = productId;
        }

        @Override
        protected ProductInfo run() throws Exception {
			/*
				TODO 查询HBase
			 */
            String productInfoJSON = "{\"id\": " + productId + ", \"name\": \"iPhone7手机\", " +
                    "\"price\": 5599, \"pictureList\":\"a.jpg,b.jpg\", \"specification\": \"iPhone7的规格\", " +
                    "\"service\": \"iPhone7的售后服务\", \"color\": \"红色,白色,黑色\", \"size\": \"5.5\", " +
                    "\"shopId\": 1, \"modifiedTime\": \"2017-01-01 12:01:00\"}";
            return JSONObject.parseObject(productInfoJSON, ProductInfo.class);
        }

        @Override
        protected ProductInfo getFallback() {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(productId);
			/*
				TODO 拼装内存数据
			 */

            return productInfo;
        }
    }
}
