package com.bd.roncoo.eshop.product.service.service.impl;

import com.bd.roncoo.eshop.product.service.mapper.ProductMapper;
import com.bd.roncoo.eshop.product.service.model.Product;
import com.bd.roncoo.eshop.product.service.rabbitmq.RabbitMQSender;
import com.bd.roncoo.eshop.product.service.rabbitmq.RabbitQueue;
import com.bd.roncoo.eshop.product.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	@Override
	public void add(Product product, String operationType) {
		productMapper.add(product); 
		
		String queue = null;
		
		if(operationType == null || "".equals(operationType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operationType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operationType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		
		rabbitMQSender.send(queue,
				"{\"event_type\": \"add\", \"data_type\": \"product\", \"id\": " + product.getId() + "}");
	}

	@Override
	public void update(Product product, String operationType) {
		productMapper.update(product);
		
		String queue = null;
		
		if(operationType == null || "".equals(operationType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operationType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operationType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		
		rabbitMQSender.send(queue,
				"{\"event_type\": \"update\", \"data_type\": \"product\", \"id\": " + product.getId() + "}");
	}

	@Override
	public void delete(Long id, String operationType) {
		productMapper.delete(id); 
		
		String queue = null;
		
		if(operationType == null || "".equals(operationType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operationType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operationType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		
		rabbitMQSender.send(queue,
				"{\"event_type\": \"delete\", \"data_type\": \"product\", \"id\": " + id + "}");
	}

	@Override
	public Product findById(Long id) {
		return productMapper.findById(id);
	}
}
