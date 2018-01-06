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
