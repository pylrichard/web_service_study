package com.bd.imooc.spring.bean.resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * 见21~23.png
 */
public class MoocResource implements ApplicationContextAware {
    /**
     * 实现了ResourceLoader接口
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void resource() throws IOException {
        /*
        	没有前缀，路径依赖于IoC容器，此处IoC容器的路径是classpath
        	见TestResource构造函数
        	也可以显式添加前缀，如classpath:
        */
        Resource resource = applicationContext.getResource("config.txt");
        System.out.println(resource.getFilename());
        System.out.println(resource.contentLength());
    }
}
