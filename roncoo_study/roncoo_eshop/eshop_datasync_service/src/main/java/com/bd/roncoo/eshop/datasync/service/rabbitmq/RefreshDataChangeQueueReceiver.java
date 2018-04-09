package com.bd.roncoo.eshop.datasync.service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * 针对数据更新问题单独创建队列，处理数据更新请求，对这些队列的消费只会在凌晨开始执行
 * 见170-商品详情页动态渲染系统-消息队列架构升级之刷数据与高优先级队列.md
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