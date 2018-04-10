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
 * 获取数据工作流程
 * 1 分发层Nginx的Lua脚本将商品id、商品店铺id转发到后端应用Nginx
 * 2 应用Nginx的Lua脚本接收到请求，获取请求参数中的商品id、商品店铺id
 * 3 根据商品id和商品店铺id，在Nginx本地缓存中获取数据
 * 4 如果在Nginx本地缓存中没有获取到数据，那么从Redis从集群中获取数据，如果获取到数据，还要设置到Nginx本地缓存中
 * 检查OpenResty对Redis Cluster支持是否足够友好
 * 否则发送http请求到缓存数据服务，基于Redis Cluster API从Redis从集群中获取数据返回给Nginx
 * 5 如果缓存数据服务没有在Redis从集群中获取到数据，就从EhCache中获取数据，返回数据给Nginx并设置Nginx本地缓存
 * 6 如果EhCache中没有数据，就从相应的服务获取数据，该服务从MySQL查询到数据后返回给Nginx，并设置到EhCache和Redis主集群
 * 7 Nginx利用获取的数据动态渲染网页模板
 * 8 将渲染后的网页模板作为http响应，返回给分发层Nginx
 *
 * 缓存数据服务管理多级缓存架构中本地EhCache+Redis主集群
 *
 * 缓存数据服务主动更新数据工作流程
 * 1 监听多个Kafka Topic，每个Topic对应一个服务
 * 2 如果一个服务发生数据变更，发送消息到Topic
 * 3 缓存数据服务监听到消息后，调用对应的服务API从MySQL获取数据
 * 4 缓存数据服务获取到数据后，将数据写入本地EhCache和Redis主集群
 *
 * MySQL数据量比Redis数据量大
 * 利用Redis的LRU淘汰机制，对key设置一个TTL过期时间，如果不是热数据就会被淘汰，以此来保证Redis中都是热数据
 */
@EnableAutoConfiguration
@SpringBootApplication
public class CacheServerApplication {
    @Bean(name = "jedisCluster")
    public JedisCluster createJedisCluster() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("192.168.8.10", 7003));
        jedisClusterNodes.add(new HostAndPort("192.168.8.10", 7004));
        jedisClusterNodes.add(new HostAndPort("192.168.8.11", 7006));
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);

        return jedisCluster;
    }

    /**
     * 注册InitListener
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean(name = "initListener")
    public ServletListenerRegistrationBean registerServletListener() {
        ServletListenerRegistrationBean servletListenerRegistrationBean =
                new ServletListenerRegistrationBean();
        //设置初始化监听器
        servletListenerRegistrationBean.setListener(new InitListener());

        return servletListenerRegistrationBean;
    }

    /**
     * 注册HystrixRequestContextFilter
     */
    @Bean(name = "hystrixRequestContextFilter")
    public FilterRegistrationBean registerFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
                new HystrixRequestContextFilter());
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    /**
     * 注册HystrixMetricsStreamServlet
     */
    @Bean(name = "hystrixMetricsStreamServlet")
    public ServletRegistrationBean registerMetricsServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                new HystrixMetricsStreamServlet());
        registration.addUrlMappings("/hystrix.stream");

        return registration;
    }

    public static void main(String[] args) {
        SpringApplication.run(CacheServerApplication.class, args);
    }
}