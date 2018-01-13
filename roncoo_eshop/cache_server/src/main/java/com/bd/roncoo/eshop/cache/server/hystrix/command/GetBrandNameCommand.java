package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.bd.roncoo.eshop.cache.server.cache.local.BrandCache;
import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取品牌名称的command
 */
public class GetBrandNameCommand extends HystrixCommand<String> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Long brandId;

    public GetBrandNameCommand(Long brandId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BrandInfoService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetBrandNameCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetBrandInfoPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(15)
                        .withQueueSizeRejectionThreshold(10))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //见95-开发品牌名称获取接口的基于本地缓存的fallback降级机制
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(15))
        );
        this.brandId = brandId;
    }

    @Override
    protected String run() throws Exception {
        //调用品牌服务API失败，开启fallback降级机制
        throw new Exception();
    }

    @Override
    protected String getFallback() {
        logger.info("从本地缓存获取过期的品牌数据，brandId=" + brandId);
        //如果访问外部依赖触发异常，fallback降级机制可以从EhCache获取数据，也可以返回默认值
        return BrandCache.getBrandName(brandId);
    }
}
