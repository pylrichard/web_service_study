package com.bd.roncoo.eshop.product.service.service.impl;

import com.bd.roncoo.eshop.product.service.mapper.CategoryMapper;
import com.bd.roncoo.eshop.product.service.model.Category;
import com.bd.roncoo.eshop.product.service.rabbitmq.RabbitMqSender;
import com.bd.roncoo.eshop.product.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分类管理服务
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private RabbitMqSender rabbitMqSender;
	
	@Override
	public void add(Category category, String operationType) {
		categoryMapper.add(category);
		String msg = createMsg("add", category.getId());
		rabbitMqSender.sendMsg(operationType, msg);
	}

	@Override
	public void update(Category category, String operationType) {
		categoryMapper.update(category);
		String msg = createMsg("update", category.getId());
		rabbitMqSender.sendMsg(operationType, msg);
	}

	@Override
	public void delete(Long id, String operationType) {
		categoryMapper.delete(id);
		String msg = createMsg("delete", id);
		rabbitMqSender.sendMsg(operationType, msg);
	}

	private String createMsg(String type, Long id) {
		return "{\"event_type\": \"" + type + "\", \"data_type\": \"category\", \"id\": " + id + "}";
	}

	@Override
	public Category findById(Long id) {
		return categoryMapper.findById(id);
	}
}
