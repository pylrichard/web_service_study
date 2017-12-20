package com.bd.imooc.spring.bean.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 见17~19.png
 */
public class MoocBeanName implements BeanNameAware, ApplicationContextAware {
    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;

        System.out.println("MoocBeanName : " + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext : " + applicationContext.getBean(this.beanName).hashCode());
    }
}