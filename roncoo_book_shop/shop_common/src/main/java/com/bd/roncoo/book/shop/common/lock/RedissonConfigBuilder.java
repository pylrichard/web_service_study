package com.bd.roncoo.book.shop.common.lock;

import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

public interface RedissonConfigBuilder {
    Config build(RedisProperties redisProperties);
}
