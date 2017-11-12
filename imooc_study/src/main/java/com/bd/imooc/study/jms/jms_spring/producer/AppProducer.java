package com.bd.imooc.study.jms.jms_spring.producer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppProducer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("producer.xml");

        ProducerService service = context.getBean(ProducerService.class);
//        ProducerService service = (ProducerService) context.getBean("service");

        for (int i = 0; i < 100; i++) {
            service.sendMessage("test" + i);
        }

        //Spring JMS会忽略连接的close()，关闭context后Spring JMS会关闭连接
        context.close();
    }
}
