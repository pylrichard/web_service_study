package com.imooc.aop.service.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogServiceTest {
    @Autowired
//    private ILogger logService;
    /*
        报错org.springframework.beans.factory.BeanNotOfRequiredTypeException
        JDK动态代理不支持类注入，只支持接口方式注入，必须使用CGLIB代理

        Spring XML配置文件
        1.使用aop配置：
        <aop:config proxy-target-class="true"> </aop:config>

        2.aspectj配置：
        <aop:aspectj-autoproxy proxy-target-class="true"/>

        3.事务annotation配置：
        <tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true"/>

        Spring Boot配置文件
        spring:
          aop:
            proxy-target-class: true

        proxy-target-class为true即使用CGLIB代理对象

        Spring的AOP代理判断逻辑
        org.springframework.aop.framework.DefaultAopProxyFactory.createAopProxy，参数AdvisedSupport是Spring AOP配置相关类
     */
    private LogService logService;

    @Test
    public void log() throws Exception {
        logService.log();
    }
}