package com.roncoo.spring_boot.util.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * maxInactiveIntervalInSeconds设置Session失效时间
 * 使用Redis Session之后，Spring Boot的server.session.timeout属性不再生效
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfiguration {}
