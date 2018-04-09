package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.bd.roncoo.eshop.cache.server.spring.SpringContext;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import redis.clients.jedis.JedisCluster;

/**
 * 见112-基于Hystrix完成对Redis访问的资源隔离以避免缓存服务被拖垮
 */
public class GetProductInfoFromRedisCacheCommand extends HystrixCommand<ProductInfo> {
    private Long productId;

    public GetProductInfoFromRedisCacheCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RedisGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        /*
                            见114-为Redis集群崩溃时的场景部署定制化的熔断策略
                         */
                        .withExecutionTimeoutInMilliseconds(100)
                        .withCircuitBreakerRequestVolumeThreshold(1000)
                        .withCircuitBreakerErrorThresholdPercentage(70)
                        .withCircuitBreakerSleepWindowInMilliseconds(60 * 1000))
        );
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        JedisCluster jedisCluster = (JedisCluster) SpringContext.getApplicationContext()
                .getBean("jedisCluster");
        String key = "product_info_" + productId;
        String json = jedisCluster.get(key);
        if (json != null) {
            return JSONObject.parseObject(json, ProductInfo.class);
        }

        return null;
    }

    /**
     * 返回null，在CacheController.getProductInfo()中会判断并从EhCache中获取数据
     */
    @Override
    protected ProductInfo getFallback() {
        return null;
    }
}
