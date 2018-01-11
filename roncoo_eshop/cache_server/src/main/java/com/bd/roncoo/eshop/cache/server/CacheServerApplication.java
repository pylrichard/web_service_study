package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.cache.server.filter.HystrixRequestContextFilter;
import com.bd.roncoo.eshop.cache.server.listener.InitListener;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 商品详情页缓存数据服务工作流程
 * 1 监听多个Kafka Topic，每个Topic对应一个服务
 * 2 如果一个服务发生数据变更，发送消息到Topic
 * 3 缓存数据服务监听到消息后，调用对应的服务API从MySQL获取数据
 * 4 缓存数据服务获取到数据后，将数据写入本地缓存(EhCache)和Redis
 */
@EnableAutoConfiguration
@SpringBootApplication
public class CacheServerApplication {
    @Bean
    public JedisCluster jedisClusterFactory() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("192.168.8.10", 7003));
        jedisClusterNodes.add(new HostAndPort("192.168.8.10", 7004));
        jedisClusterNodes.add(new HostAndPort("192.168.8.11", 7006));
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);

        return jedisCluster;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean servletListenerRegistrationBean =
                new ServletListenerRegistrationBean();
        //设置初始化监听器
        servletListenerRegistrationBean.setListener(new InitListener());

        return servletListenerRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
                new HystrixRequestContextFilter());
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean indexServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        registration.addUrlMappings("/hystrix.stream");

        return registration;
    }

    public static void main(String[] args) {
        SpringApplication.run(CacheServerApplication.class, args);
    }
}