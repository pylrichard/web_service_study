package com.bd.imooc.study.jms.spring.producer;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProducerTest {
    private static ApplicationContext context = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        context = new ClassPathXmlApplicationContext("producer.xml");
    }

    @Test
    public void test() {
        ProducerService service = context.getBean(ProducerService.class);
//        ProducerService service = (ProducerService) context.getBean("service");

        for (int i = 0; i < 100; i++) {
            service.sendMessage("test" + i);
        }
    }
}
