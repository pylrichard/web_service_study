package com.bd.roncoo.eshop.product.service.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {  
    @Autowired
    private AmqpTemplate rabbitTemplate;  
   
    public void send(String queue, String message) {  
        this.rabbitTemplate.convertAndSend(queue, message);  
    }

    public void sendMsg(String operationType, String msg) {
        String queue = null;
        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        send(queue, msg);
    }
}