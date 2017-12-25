package com.bd.roncoo.eshop.product.service.service.impl;

import com.bd.roncoo.eshop.product.service.mapper.ProductSpecificationMapper;
import com.bd.roncoo.eshop.product.service.model.ProductSpecification;
import com.bd.roncoo.eshop.product.service.rabbitmq.RabbitMQSender;
import com.bd.roncoo.eshop.product.service.rabbitmq.RabbitQueue;
import com.bd.roncoo.eshop.product.service.service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {
	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	@Override
	public void add(ProductSpecification productSpecification, String operationType) {
		productSpecificationMapper.add(productSpecification); 
		
		String queue = null;
		
		if(operationType == null || "".equals(operationType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operationType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operationType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		
		rabbitMQSender.send(queue,
				"{\"event_type\": \"add\", \"data_type\": \"product_specification\", \"id\": " + productSpecification.getId()
						+ ", \"product_id\": " + productSpecification.getProductId() + "}");
	}

	@Override
	public void update(ProductSpecification productSpecification, String operationType) {
		productSpecificationMapper.update(productSpecification); 
		
		String queue = null;
		
		if(operationType == null || "".equals(operationType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operationType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operationType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		
		rabbitMQSender.send(queue,
				"{\"event_type\": \"update\", \"data_type\": \"product_specification\", \"id\": " + productSpecification.getId()
						+ ", \"product_id\": " + productSpecification.getProductId() + "}");
	}

	@Override
	public void delete(Long id, String operationType) {
		ProductSpecification productSpecification = findById(id);
		productSpecificationMapper.delete(id); 
		
		String queue = null;
		
		if(operationType == null || "".equals(operationType)) {
			queue = RabbitQueue.DATA_CHANGE_QUEUE;
		} else if("refresh".equals(operationType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if("high".equals(operationType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		
		rabbitMQSender.send(queue,
				"{\"event_type\": \"delete\", \"data_type\": \"product_specification\", \"id\": " + id
						+ ", \"product_id\": " + productSpecification.getProductId() + "}");
	}

	@Override
	public ProductSpecification findById(Long id) {
		return productSpecificationMapper.findById(id);
	}
	
	@Override
	public ProductSpecification findByProductId(Long productId) {
		return productSpecificationMapper.findById(productId);
	}
}
