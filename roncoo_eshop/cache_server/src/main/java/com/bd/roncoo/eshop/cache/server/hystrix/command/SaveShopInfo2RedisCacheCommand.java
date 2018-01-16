package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.cache.server.model.ShopInfo;
import com.bd.roncoo.eshop.cache.server.spring.SpringContext;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import redis.clients.jedis.JedisCluster;

/**
 * 见112-基于Hystrix完成对Redis访问的资源隔离以避免缓存服务被拖垮
 */
public class SaveShopInfo2RedisCacheCommand extends HystrixCommand<Boolean> {
    private ShopInfo shopInfo;

    public SaveShopInfo2RedisCacheCommand(ShopInfo shopInfo) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RedisGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(100)
                        .withCircuitBreakerRequestVolumeThreshold(1000)
                        .withCircuitBreakerErrorThresholdPercentage(70)
                        .withCircuitBreakerSleepWindowInMilliseconds(60 * 1000))
        );
        this.shopInfo = shopInfo;
    }

    @Override
    protected Boolean run() throws Exception {
        JedisCluster jedisCluster = (JedisCluster) SpringContext.getApplicationContext()
                .getBean("jedisClusterFactory");
        String key = "shop_info_" + shopInfo.getId();
        jedisCluster.set(key, JSONObject.toJSONString(shopInfo));

        return true;
    }

    @Override
    protected Boolean getFallback() {
        return false;
    }
}
