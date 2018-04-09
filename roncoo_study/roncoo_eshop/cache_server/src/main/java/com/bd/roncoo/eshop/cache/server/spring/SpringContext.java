package com.bd.roncoo.eshop.cache.server.spring;

import org.springframework.context.ApplicationContext;

public class SpringContext {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContext.applicationContext = applicationContext;
    }
}
