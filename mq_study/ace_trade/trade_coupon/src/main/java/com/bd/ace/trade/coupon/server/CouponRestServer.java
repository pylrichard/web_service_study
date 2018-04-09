package com.bd.ace.trade.coupon.server;

import com.bd.ace.trade.common.constant.TradeEnums;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class CouponRestServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(TradeEnums.RestServerEnum.COUPON.getServerPort());
        ServletContextHandler springMvcHandler = new ServletContextHandler();
        springMvcHandler.setContextPath("/" + TradeEnums.RestServerEnum.COUPON.getContextPath());
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("classpath:xml/spring-web-coupon.xml");
        springMvcHandler.addEventListener(new ContextLoaderListener(context));
        springMvcHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/*");
        server.setHandler(springMvcHandler);
        server.start();
        server.join();
    }
}
