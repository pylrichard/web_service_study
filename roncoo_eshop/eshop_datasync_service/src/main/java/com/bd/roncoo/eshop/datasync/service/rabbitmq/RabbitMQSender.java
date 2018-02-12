package com.bd.roncoo.eshop.datasync.service.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生产者
 */
@Component  
public class RabbitMqSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;  
   
    public void send(String topic, String message) {  
        this.rabbitTemplate.convertAndSend(topic, message);  
    }  
}