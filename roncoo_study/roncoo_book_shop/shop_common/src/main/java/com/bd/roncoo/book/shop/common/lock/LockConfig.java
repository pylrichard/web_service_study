package com.bd.roncoo.book.shop.common.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class LockConfig {
    @Autowired
    private Map<String, RedissonConfigBuilder> redissonConfigBuilders;

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        RedissonConfigBuilder redissonConfigBuilder;
        if (redisProperties.getCluster() == null) {
            redissonConfigBuilder = redissonConfigBuilders.get("singleServerRedissonConfigBuilder");
        } else {
            redissonConfigBuilder = redissonConfigBuilders.get("clusterServerRedissonConfigBuilder");
        }

        return Redisson.create(redissonConfigBuilder.build(redisProperties));
    }
}
