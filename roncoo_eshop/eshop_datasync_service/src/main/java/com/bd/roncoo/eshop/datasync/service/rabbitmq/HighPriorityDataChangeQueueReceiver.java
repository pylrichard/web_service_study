package com.bd.roncoo.eshop.datasync.service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component  
@RabbitListener(queues = "high-priority-data-change-queue")  
public class HighPriorityDataChangeQueueReceiver extends QueueReceiver {
	public HighPriorityDataChangeQueueReceiver() {
		super("high-priority-aggr-data-change-queue");
	}
	
    @RabbitHandler  
    public void process(String message) {  
    	super.processMsg(message);
    }
}  