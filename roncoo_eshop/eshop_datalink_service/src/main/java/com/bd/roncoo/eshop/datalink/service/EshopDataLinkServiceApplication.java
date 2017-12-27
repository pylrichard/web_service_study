package com.bd.roncoo.eshop.datalink.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 见175-商品详情页动态渲染系统-基于Spring Cloud开发数据直连服务.md
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class EshopDataLinkServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EshopDataLinkServiceApplication.class, args); 
	}
	
	@Bean
	public JedisPool jedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(100);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000 * 10); 
		config.setTestOnBorrow(true);
		return new JedisPool(config, "192.168.8.10", 1111);
	}
}
