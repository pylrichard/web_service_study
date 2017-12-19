package com.bd.roncoo.book.shop.common.lock;

import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Component;

@Component("singleServerRedissonConfigBuilder")
public class SingleServerRedissonConfigBuilder implements RedissonConfigBuilder {
    @Override
    public Config build(RedisProperties redisProperties) {
        Config config = new Config();
        config.useSingleServer().setAddress(redisProperties.getHost() + ":" + redisProperties.getPort());

        return config;
    }
}
