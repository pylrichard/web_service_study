package com.bd.ace.trade.user.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.bd.ace.trade.common.constant.TradeEnums;

public class UserRestServer {
    public static void main(String[] args) throws Exception {
        /*
            自定义实现web.xml功能
         */
        Server server = new Server(TradeEnums.RestServerEnum.USER.getServerPort());
        ServletContextHandler springMvcHandler = new ServletContextHandler();
        springMvcHandler.setContextPath("/" + TradeEnums.RestServerEnum.USER.getContextPath());
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("classpath:xml/spring-web-user.xml");
        springMvcHandler.addEventListener(new ContextLoaderListener(context));
        springMvcHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/*");
        server.setHandler(springMvcHandler);
        server.start();
        //避免线程退出
        server.join();
    }
}
