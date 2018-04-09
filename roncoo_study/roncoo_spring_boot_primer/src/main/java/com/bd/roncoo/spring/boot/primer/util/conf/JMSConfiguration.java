package com.bd.roncoo.spring.boot.primer.util.conf;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class JMSConfiguration {
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("roncoo_spring_boot.queue");
    }
}
