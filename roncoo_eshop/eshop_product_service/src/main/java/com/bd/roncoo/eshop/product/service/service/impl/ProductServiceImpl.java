package com.bd.roncoo.eshop.product.service.service.impl;

import com.bd.roncoo.eshop.product.service.mapper.ProductMapper;
import com.bd.roncoo.eshop.product.service.model.Product;
import com.bd.roncoo.eshop.product.service.rabbitmq.RabbitMqSender;
import com.bd.roncoo.eshop.product.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品信息管理服务
 */
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private RabbitMqSender rabbitMqSender;
	
	@Override
	public void add(Product product, String operationType) {
		productMapper.add(product);
		String msg = createMsg("add", product.getId());
		rabbitMqSender.sendMsg(operationType, msg);
	}

	@Override
	public void update(Product product, String operationType) {
		productMapper.update(product);
		String msg = createMsg("update", product.getId());
		rabbitMqSender.sendMsg(operationType, msg);
	}

	@Override
	public void delete(Long id, String operationType) {
		productMapper.delete(id);
		String msg = createMsg("delete", id);
		rabbitMqSender.sendMsg(operationType, msg);
	}

	private String createMsg(String type, Long id) {
		return "{\"event_type\": \"" + type + "\", \"data_type\": \"product\", \"id\": " + id + "}";
	}

	@Override
	public Product findById(Long id) {
		return productMapper.findById(id);
	}
}
