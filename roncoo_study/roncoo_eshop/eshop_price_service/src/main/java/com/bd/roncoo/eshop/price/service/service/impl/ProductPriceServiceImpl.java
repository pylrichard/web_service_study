package com.bd.roncoo.eshop.price.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.price.service.mapper.ProductPriceMapper;
import com.bd.roncoo.eshop.price.service.model.ProductPrice;
import com.bd.roncoo.eshop.price.service.service.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {
	@Autowired
	private ProductPriceMapper productPriceMapper;
	@Autowired
	private JedisPool jedisPool;
	
	@Override
	public void add(ProductPrice productPrice) {
		productPriceMapper.add(productPrice); 
		Jedis jedis = jedisPool.getResource();
		jedis.set("product_price_" + productPrice.getProductId(), JSONObject.toJSONString(productPrice));
	}

	@Override
	public void update(ProductPrice productPrice) {
		productPriceMapper.update(productPrice);
		Jedis jedis = jedisPool.getResource();
		jedis.set("product_price_" + productPrice.getProductId(), JSONObject.toJSONString(productPrice));
	}

	@Override
	public void delete(Long id) {
		ProductPrice productPrice = findById(id);
		productPriceMapper.delete(id); 
		Jedis jedis = jedisPool.getResource();
		jedis.del("product_price_" + productPrice.getProductId());
	}

	@Override
	public ProductPrice findById(Long id) {
		/*
			先查询Redis，如果没有获取到数据再查询MySQL，获取到数据后刷回Redis
			即MySQL+Redis双写一致性问题+解决方案
		*/
		return productPriceMapper.findById(id);
	}

	/**
	 * OneService调用此API，获取实时价格
	 */
	@Override
	public ProductPrice findByProductId(Long productId) {
		Jedis jedis = jedisPool.getResource();
		String dataJSON = jedis.get("product_price_" + productId);
		if(dataJSON != null && !"".equals(dataJSON)) {
			JSONObject dataJSONObject = JSONObject.parseObject(dataJSON);
			dataJSONObject.put("id", "-1");  
			return JSONObject.parseObject(dataJSONObject.toJSONString(), ProductPrice.class);
		} else {
			return productPriceMapper.findByProductId(productId);
		}
	}
}
