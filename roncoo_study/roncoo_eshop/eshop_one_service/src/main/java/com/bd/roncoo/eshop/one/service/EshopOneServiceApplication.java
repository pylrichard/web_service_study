package com.bd.roncoo.eshop.one.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 见186-商品详情页OneService系统-整体架构设计.png
 *
 * 商品详情页依赖的服务达到几十个，统一访问入口，方便前端调用，打造服务闭环
 *
 * 请求预处理
 * 比如库存状态(有货/无货)的转换，第三方运费的处理，第三方配送时效(多少天发货)的处理
 * 处理主商品与配件关系，比如iPhone可能搭载着耳机，充电器等
 * 商家运费动态计算
 * 请求预处理做一层封装和代理，进行业务逻辑的判断和处理
 * 这样可以给后端服务传递更多参数，或者简化后端服务的计算逻辑
 *
 * 合并接口调用，减少ajax异步加载次数
 * 获取页面中相关联的数据，发送一次请求发给OneService的统一API，此API统一调用各个后端服务API
 * 减少浏览器和后端服务间的交互次数
 * 比如促销服务和广告服务合并成一个API，发送一次请求获取两份数据
 * 库存服务和配送服务合并成一个API，发送一次请求获取当前库存，如何配送
 * 组合推荐服务+配件推荐服务+套装推荐服务合并成一个API，合并结果
 *
 * 通过Hystrix统一监控、统一降级(限流、自动熔断、调用失败都会进行降级)
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
public class EshopOneServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EshopOneServiceApplication.class, args);
	}
}
