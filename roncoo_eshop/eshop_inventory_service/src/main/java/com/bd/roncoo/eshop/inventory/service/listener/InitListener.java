package com.bd.roncoo.eshop.inventory.service.listener;

import com.bd.roncoo.eshop.inventory.service.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener监听Java Web应用初始化
 */
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //初始化工作线程池和内存队列
        RequestProcessorThreadPool.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
