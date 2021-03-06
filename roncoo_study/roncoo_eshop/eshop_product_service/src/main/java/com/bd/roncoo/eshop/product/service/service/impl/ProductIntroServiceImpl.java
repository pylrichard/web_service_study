package com.bd.roncoo.eshop.product.service.service.impl;

import com.bd.roncoo.eshop.product.service.mapper.ProductIntroMapper;
import com.bd.roncoo.eshop.product.service.model.ProductIntro;
import com.bd.roncoo.eshop.product.service.rabbitmq.RabbitMqSender;
import com.bd.roncoo.eshop.product.service.service.ProductIntroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品介绍管理服务
 */
@Service
public class ProductIntroServiceImpl implements ProductIntroService {
	@Autowired
	private ProductIntroMapper productIntroMapper;
	@Autowired
	private RabbitMqSender rabbitMqSender;
	
	@Override
	public void add(ProductIntro productIntro, String operationType) {
		productIntroMapper.add(productIntro); 
		String msg = createMsg("add", productIntro.getId(), productIntro.getProductId());
		rabbitMqSender.send(operationType, msg);
	}

	@Override
	public void update(ProductIntro productIntro, String operationType) {
		productIntroMapper.update(productIntro);
		String msg = createMsg("update", productIntro.getId(), productIntro.getProductId());
		rabbitMqSender.send(operationType, msg);
	}

	@Override
	public void delete(Long id, String operationType) {
		ProductIntro productIntro = findById(id);
		productIntroMapper.delete(id);
		String msg = createMsg("delete", id, productIntro.getProductId());
		rabbitMqSender.send(operationType, msg);
	}

	private String createMsg(String type, Long id, Long productId) {
		return "{\"event_type\": \"" + type + "\", \"data_type\": \"product_intro\", \"id\": " + id
				+ ", \"product_id\": " + productId + "}";
	}

	@Override
	public ProductIntro findById(Long id) {
		return productIntroMapper.findById(id);
	}
}
