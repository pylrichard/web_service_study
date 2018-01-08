package com.bd.roncoo.eshop.inventory.service;

import com.bd.roncoo.eshop.inventory.service.listener.InitListener;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 缓存与数据库双写一致性保障方案
 * 1 线程池+内存队列初始化见InitListener、RequestProcessorThreadPool、RequestProcessorThread、RequestQueue
 * 2 读请求见ProductInventoryCacheRefreshRequest(从数据库读取数据更新到缓存)、写请求见ProductInventoryDbUpdateRequest(更新数据库)
 * 3 读/写请求异步执行见RequestAsyncProcessServiceImpl
 * 4 写请求处理见ProductInventoryController.update()、读请求处理见findByProductId()
 * 5 读请求去重优化见RequestProcessorThread
 * <p>
 * 先根据1理解初始化流程，再根据4理解写请求/读请求处理流程
 */
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@MapperScan("com.bd.roncoo.eshop.inventory.service.mapper")
@EnableEurekaClient
public class EshopInventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EshopInventoryServiceApplication.class, args);
    }

    /**
     * 注册监听器
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean servletListenerRegistrationBean =
                new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new InitListener());

        return servletListenerRegistrationBean;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DataSource();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
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
