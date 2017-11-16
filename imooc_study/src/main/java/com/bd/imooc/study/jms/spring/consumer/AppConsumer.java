package com.bd.imooc.study.jms.spring.consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppConsumer {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");

        //关闭context可能导致消息接收不全，消息接收是异步的
//        context.close();
    }
}
