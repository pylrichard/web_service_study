package com.bd.imooc.spring.bean.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 见spring-lifecycle.xml，见12~16.png
 */
public class BeanLifeCycle implements InitializingBean, DisposableBean {
    /**
     * 全局初始化方法
     */
    public void defaultInit() {
        System.out.println("Bean defaultInit.");
    }

    /**
     * 全局销毁方法
     */
    public void defaultDestroy() {
        System.out.println("Bean defaultDestroy.");
    }

    /**
     * 先于stop()(destroy-method)运行
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("Bean destroy.");
    }

    /**
     * 私有初始化方法，先于start()(init-method)运行
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Bean afterPropertiesSet.");
    }

    /**
     * 覆盖全局方法
     */
    public void start() {
        System.out.println("Bean start .");
    }

    public void stop() {
        System.out.println("Bean stop.");
    }
}
