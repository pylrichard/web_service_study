package com.bd.imooc.spring.base;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

public class UnitTestBase {
    private ClassPathXmlApplicationContext context;

    private String springXmlPath;

    public UnitTestBase() {
    }

    public UnitTestBase(String springXmlPath) {
        this.springXmlPath = springXmlPath;
    }

    //运行单元测试方法之前运行
    @Before
    public void before() {
        if (StringUtils.isEmpty(springXmlPath)) {
            springXmlPath = "classpath*:spring-*.xml";
        }
        try {
            //创建1个Bean容器，Bean容器初始化见慕课-Spring入门篇/1~2.png
            context = new ClassPathXmlApplicationContext(springXmlPath.split("[,\\s]+"));
            context.start();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    //运行单元测试方法之后运行
    @After
    public void after() {
        context.destroy();
    }

    @SuppressWarnings("unchecked")
    protected <T extends Object> T getBean(String beanId) {
        try {
            return (T) context.getBean(beanId);
        } catch (BeansException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected <T extends Object> T getBean(Class<T> clazz) {
        try {
            return context.getBean(clazz);
        } catch (BeansException e) {
            e.printStackTrace();
            return null;
        }
    }
}
