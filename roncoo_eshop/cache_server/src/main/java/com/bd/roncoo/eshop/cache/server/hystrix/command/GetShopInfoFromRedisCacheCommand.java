package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.cache.server.model.ShopInfo;
import com.bd.roncoo.eshop.cache.server.spring.SpringContext;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import redis.clients.jedis.JedisCluster;

public class GetShopInfoFromRedisCacheCommand extends HystrixCommand<ShopInfo> {
    private Long shopId;

    public GetShopInfoFromRedisCacheCommand(Long shopId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RedisGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(100)
                        .withCircuitBreakerRequestVolumeThreshold(1000)
                        .withCircuitBreakerErrorThresholdPercentage(70)
                        .withCircuitBreakerSleepWindowInMilliseconds(60 * 1000))
        );
        this.shopId = shopId;
    }

    @Override
    protected ShopInfo run() throws Exception {
        JedisCluster jedisCluster = (JedisCluster) SpringContext.getApplicationContext()
                .getBean("jedisClusterFactory");
        String key = "shop_info_" + shopId;
        String json = jedisCluster.get(key);
        if (json != null) {
            return JSONObject.parseObject(json, ShopInfo.class);
        }

        return null;
    }

    @Override
    protected ShopInfo getFallback() {
        return null;
    }
}
