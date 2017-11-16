package com.bd.ace.trade.pay.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.bd.ace.trade.common.constant.TradeEnums;

public class PayRestServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(TradeEnums.RestServerEnum.PAY.getServerPort());
        ServletContextHandler springMvcHandler = new ServletContextHandler();
        springMvcHandler.setContextPath("/" + TradeEnums.RestServerEnum.PAY.getContextPath());
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("classpath:xml/spring-web-pay.xml");
        springMvcHandler.addEventListener(new ContextLoaderListener(context));
        springMvcHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/*");
        server.setHandler(springMvcHandler);
        server.start();
        server.join();
    }
}
