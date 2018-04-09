package com.bd.roncoo.eshop.product.service.rabbitmq;

public interface RabbitQueue {
	String DATA_CHANGE_QUEUE = "data-change-queue";
	String REFRESH_DATA_CHANGE_QUEUE = "refresh-data-change-queue";
	String HIGH_PRIORITY_DATA_CHANGE_QUEUE = "high-priority-data-change-queue";
}
