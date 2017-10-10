package com.roncoo.spring_boot.util.conf;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisCacheConfiguration extends CachingConfigurerSupport {
    /**
     * 自定义缓存管理器
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        //设置默认过期时间
        cacheManager.setDefaultExpiration(20);
        Map<String, Long> expires = new HashMap<>();
        expires.put("user_log_cache", 200L);
        cacheManager.setExpires(expires);

        return cacheManager;
    }

    /**
     * 自定义key，根据类名+方法名+所有参数的值生成唯一key，即使@Cacheable中的value属性一样，key也会不一样
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder builder = new StringBuilder();

                builder.append(o.getClass().getName());
                builder.append(method.getName());
                for (Object obj : objects) {
                    builder.append(obj.toString());
                }

                return builder.toString();
            }
        };
    }
}
