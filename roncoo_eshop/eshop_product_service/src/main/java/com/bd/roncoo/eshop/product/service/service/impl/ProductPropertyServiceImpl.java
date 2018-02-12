package com.bd.roncoo.eshop.product.service.service.impl;

import com.bd.roncoo.eshop.product.service.mapper.ProductPropertyMapper;
import com.bd.roncoo.eshop.product.service.model.ProductProperty;
import com.bd.roncoo.eshop.product.service.rabbitmq.RabbitMqSender;
import com.bd.roncoo.eshop.product.service.service.ProductPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品属性管理服务
 */
@Service
public class ProductPropertyServiceImpl implements ProductPropertyService {
	@Autowired
	private ProductPropertyMapper productPropertyMapper;
	@Autowired
	private RabbitMqSender rabbitMqSender;
	
	@Override
	public void add(ProductProperty productProperty, String operationType) {
		productPropertyMapper.add(productProperty); 
		String msg = createMsg("add", productProperty.getId(), productProperty.getProductId());
		rabbitMqSender.sendMsg(operationType, msg);
	}

	@Override
	public void update(ProductProperty productProperty, String operationType) {
		productPropertyMapper.update(productProperty);
		String msg = createMsg("update", productProperty.getId(), productProperty.getProductId());
		rabbitMqSender.sendMsg(operationType, msg);
	}

	@Override
	public void delete(Long id, String operationType) {
		ProductProperty productProperty = findById(id);
		productPropertyMapper.delete(id);
		String msg = createMsg("delete", id, productProperty.getProductId());
		rabbitMqSender.sendMsg(operationType, msg);
	}

	private String createMsg(String type, Long id, Long productId) {
		return "{\"event_type\": \"" + type + "\", \"data_type\": \"product_property\", \"id\": " + id
				+ ", \"product_id\": " + productId + "}";
	}

	@Override
	public ProductProperty findById(Long id) {
		return productPropertyMapper.findById(id);
	}
	
	@Override
	public ProductProperty findByProductId(Long productId) {
		return productPropertyMapper.findByProductId(productId);
	}
}
