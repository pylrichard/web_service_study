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
public class SaveProductInfo2RedisCacheCommand extends HystrixCommand<Boolean> {
    private ProductInfo productInfo;

    public SaveProductInfo2RedisCacheCommand(ProductInfo productInfo) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RedisGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(100)
                        .withCircuitBreakerRequestVolumeThreshold(1000)
                        .withCircuitBreakerErrorThresholdPercentage(70)
                        .withCircuitBreakerSleepWindowInMilliseconds(60 * 1000))
        );
        this.productInfo = productInfo;
    }

    @Override
    protected Boolean run() throws Exception {
        JedisCluster jedisCluster = (JedisCluster) SpringContext.getApplicationContext()
                .getBean("jedisClusterFactory");
        String key = "product_info_" + productInfo.getId();
        jedisCluster.set(key, JSONObject.toJSONString(productInfo));

        return true;
    }

    @Override
    protected Boolean getFallback() {
        return false;
    }
}
