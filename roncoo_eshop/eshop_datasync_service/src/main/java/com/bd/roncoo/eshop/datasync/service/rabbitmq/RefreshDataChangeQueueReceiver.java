package com.bd.roncoo.eshop.datasync.service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 */
@Component  
@RabbitListener(queues = "refresh-data-change-queue")  
public class RefreshDataChangeQueueReceiver extends QueueReceiver {
	public RefreshDataChangeQueueReceiver() {
		super("refresh-aggr-data-change-queue");
	}
	
    @RabbitHandler  
    public void process(String message) {  
    	super.processMsg(message);
    }  
}