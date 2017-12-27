package com.bd.roncoo.eshop.datasync.service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 业务系统将高优先级的消息写到高优先级队列中
 * 见170-商品详情页动态渲染系统-消息队列架构升级之刷数据与高优先级队列.md
 */
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