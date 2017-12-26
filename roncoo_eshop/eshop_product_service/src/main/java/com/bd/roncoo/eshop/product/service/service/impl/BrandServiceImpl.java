package com.bd.roncoo.eshop.product.service.service.impl;

import com.bd.roncoo.eshop.product.service.mapper.BrandMapper;
import com.bd.roncoo.eshop.product.service.model.Brand;
import com.bd.roncoo.eshop.product.service.rabbitmq.RabbitMQSender;
import com.bd.roncoo.eshop.product.service.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	@Override
	public void add(Brand brand, String operationType) {
		brandMapper.add(brand);
		String msg = createMsg("add", brand.getId());
		rabbitMQSender.sendMsg(operationType, msg);
	}
	
	@Override
	public void update(Brand brand, String operationType) {
		brandMapper.update(brand);
		String msg = createMsg("update", brand.getId());
		rabbitMQSender.sendMsg(operationType, msg);
	}

	@Override
	public void delete(Long id, String operationType) {
		brandMapper.delete(id);
		String msg = createMsg("delete", id);
		rabbitMQSender.sendMsg(operationType, msg);
	}

	private String createMsg(String type, Long id) {
		return "{\"event_type\": \"" + type + "\", \"data_type\": \"brand\", \"id\": " + id + "}";
	}

	@Override
	public Brand findById(Long id) {
		return brandMapper.findById(id);
	}

	@Override
	public List<Brand> findByIds(String ids) {
		return brandMapper.findByIds(ids);
	}
}
