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
 *
 * 运行在独立线程池的线程中
 *
 * 工作原理和执行步骤见93-深入分析Hystrix执行时的8大流程步骤以及内部原理
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {
    public static final HystrixCommandKey KEY = HystrixCommandKey.Factory.asKey("GetProductInfoCommand");
    private Long productId;

    public GetProductInfoCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductService"))
                .andCommandKey(KEY)
                /*
                    默认的thread pool key是command group名称
                    对同一个服务的不同接口使用独立的线程池，见92-Hystrix的线程池+服务+接口划分以及资源池的容量大小控制

                    设置线程池参数
                 */
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductInfoPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        //线程池大小
                        .withCoreSize(8)
                        .withMaximumSize(30)
                        .withAllowMaximumSizeToDivergeFromCoreSize(true)
                        .withKeepAliveTimeMinutes(1)
                        //等待队列大小，不设置withMaxQueueSize和withQueueSizeRejectionThreshold，等待队列是关闭的
                        .withMaxQueueSize(10)
                        /*
                            command在提交到线程池之前，会先进入一个队列中，队列满之后才会reject之后的command
                            如果withMaxQueueSize < withQueueSizeRejectionThreshold
                            那么withQueueSizeRejectionThreshold = withMaxQueueSize
                            可以接受8(线程池执行) + 10(等待队列缓冲)个请求，多余的请求进入fallback降级
                            线程池内有线程空闲了，等待队列中的请求没有timeout，就交给空闲线程执行
                         */
                        .withQueueSizeRejectionThreshold(8))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //见96-深入理解Hystrix的短路器执行原理以及模拟接口异常时的短路实验
                        .withCircuitBreakerRequestVolumeThreshold(30)
                        .withCircuitBreakerErrorThresholdPercentage(40)
                        .withCircuitBreakerSleepWindowInMilliseconds(3000)
                        /*
                            默认是1000ms
                            timeout设置大一些，否则请求在等待队列中时间一长就会触发timeout(进入fallback降级)
                            等不到线程池去执行
                         */
                        .withExecutionTimeoutInMilliseconds(500)
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(30))
        );
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        //见96-深入理解Hystrix的短路器执行原理以及模拟接口异常时的短路实验
        if (productId.equals(-1L)) {
            throw new Exception();
        }
        //见97-深入理解线程池隔离技术的设计原则以及动手实战接口限流实验、98-基于timeout机制为商品服务接口的调用超时提供安全保护
        if (productId.equals(-2L)) {
            Thread.sleep(1000);
        }
        //发送请求调用商品服务API
        String url = "http://localhost:8082/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);

        return JSONObject.parseObject(response, ProductInfo.class);
    }

    /**
     * 手动清理缓存
     */
    public static void flushCache(Long productId) {
        HystrixRequestCache.getInstance(KEY,
                HystrixConcurrencyStrategyDefault.getInstance()).clear("product_info_" + productId);
    }

    /**
     * 获取缓存key
     * 见94-基于request cache请求缓存技术优化批量商品数据查询接口
     */
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

    private static class HBaseColdDataCommand extends HystrixCommand<ProductInfo> {
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
