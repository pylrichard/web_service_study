package com.bd.imooc.spring.bean.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MoocApplicationContext implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    /**
     * 获取ApplicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //与UnitTestBase.context是同一个Bean容器
        this.applicationContext = applicationContext;

        System.out.println("MoocApplicationContext : " + applicationContext.getBean("moocApplicationContext").hashCode());
    }
}
