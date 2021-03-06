package com.bd.roncoo.spring.boot.primer.util.conf;

import org.springframework.context.annotation.Configuration;

/**
 * maxInactiveIntervalInSeconds设置Session失效时间
 * 使用Redis Session之后，Spring Boot的server.session.timeout属性不再生效
 */
@Configuration
public class SessionConfiguration {}
