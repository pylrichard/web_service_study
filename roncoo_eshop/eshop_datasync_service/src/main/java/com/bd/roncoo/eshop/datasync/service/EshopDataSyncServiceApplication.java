package com.bd.roncoo.eshop.datasync.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 数据同步服务获取各种原子数据的变更消息
 *
 * 1 通过Feign调用eshop_product_service服务的接口获取数据
 * 2 将原子数据在Redis中进行增删改
 * 3 将维度数据变化消息写入RabbitMQ中另外一个Queue，供数据聚合服务消费
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class EshopDataSyncServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EshopDataSyncServiceApplication.class, args); 
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
