package com.bd.imooc.free.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/**
 * 注解@SpringBootApplication包含@ComponentScan(basePackagesi = xxx)加载包
 * mybatis-spring包含注解@MapperScan加载mapper
 *
 * DataSourceProperties包含注解@ConfigurationProperties(prefix = "spring.datasource")解析配置
 * Ctrl+H调出Find in Path/Scope进行全文搜索
 *
 * spring-boot-autoconfigure.spring-configuration-metadata.json中
 * server.port由ServerProperties(继承EmbeddedServletContainerCustomizer)解析
 * F4调用Type Hierarchy查看接口实现/类继承
 *
 * AbstractConfigurableEmbeddedServletContainer.port默认为8080
 */
@SpringBootApplication
@EnableCaching
public class SpringBootAOPApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAOPApplication.class, args);
    }
}
