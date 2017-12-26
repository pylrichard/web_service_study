package com.bd.roncoo.eshop.dataaggr.service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component  
@RabbitListener(queues = "high-priority-aggr-data-change-queue")  
public class HighPriorityAggrDataChangeQueueReceiver extends QueueReceiver {
    @RabbitHandler
    public void process(String message) {  
    	super.processMsg(message);
    }  
}