package com.bd.roncoo.book.shop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行schema-mysql.sql创建相应数据库，注意大写
 * application.properties先设置端口号为8060，启动一个服务进程
 * 设置端口号为8061，再启动一个服务进程
 * 此时先访问http://localhost:8060/admin/，登录成功后
 * 再访问http://localhost:8061/admin/，不需要再次登录
 * 注解@EnableJdbcHttpSession实现集群session状态共享
 */
@SpringBootApplication
@EnableSwagger2
//注解@EnableCaching和@EnableScheduling需要在服务提供者和服务消费者都进行添加
@EnableCaching
@EnableScheduling
@ImportResource("classpath:consumer.xml")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Bean
    public CacheManagerCustomizer<RedisCacheManager> customizer() {
        return new CacheManagerCustomizer<RedisCacheManager>() {
            @Override
            public void customize(RedisCacheManager cacheManager) {
                /*
                    设置所有数据的过期时间为10s
                 */
                cacheManager.setDefaultExpiration(10L);

                /*
                    为每个缓存设置过期时间
                    为每个key设置过期时间需要自行实现CacheManager
                 */
                Map<String, Long> map = new HashMap<>(16);
                map.put("books", 100L);
                map.put("users", 200L);
                cacheManager.setExpires(map);
            }
        };
    }
}
