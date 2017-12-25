package com.bd.roncoo.eshop.product.service.rabbitmq;

public interface RabbitQueue {
	static final String DATA_CHANGE_QUEUE = "data-change-queue";
	static final String REFRESH_DATA_CHANGE_QUEUE = "refresh-data-change-queue";
	static final String HIGH_PRIORITY_DATA_CHANGE_QUEUE = "high-priority-data-change-queue";
}
