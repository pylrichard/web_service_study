package com.bd.roncoo.eshop.product.service.service.impl;

import com.bd.roncoo.eshop.product.service.mapper.ProductSpecificationMapper;
import com.bd.roncoo.eshop.product.service.model.ProductSpecification;
import com.bd.roncoo.eshop.product.service.rabbitmq.RabbitMqSender;
import com.bd.roncoo.eshop.product.service.service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品规格管理服务
 */
@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {
	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;
	@Autowired
	private RabbitMqSender rabbitMqSender;
	
	@Override
	public void add(ProductSpecification productSpecification, String operationType) {
		productSpecificationMapper.add(productSpecification);
		String msg = createMsg("add", productSpecification.getId(), productSpecification.getProductId());
		rabbitMqSender.sendMsg(operationType, msg);
	}

	@Override
	public void update(ProductSpecification productSpecification, String operationType) {
		productSpecificationMapper.update(productSpecification); 
		String msg = createMsg("update", productSpecification.getId(), productSpecification.getProductId());
		rabbitMqSender.sendMsg(operationType, msg);
	}

	@Override
	public void delete(Long id, String operationType) {
		ProductSpecification productSpecification = findById(id);
		productSpecificationMapper.delete(id);
		String msg = createMsg("delete", id, productSpecification.getProductId());
		rabbitMqSender.sendMsg(operationType, msg);
	}

	private String createMsg(String type, Long id, Long productId) {
		return "{\"event_type\": \"" + type + "\", \"data_type\": \"product_specification\", \"id\": " + id
				+ ", \"product_id\": " + productId + "}";
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
