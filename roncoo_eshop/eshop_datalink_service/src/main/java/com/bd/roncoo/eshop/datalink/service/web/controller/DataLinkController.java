package com.bd.roncoo.eshop.datalink.service.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.datalink.service.service.EshopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
public class DataLinkController {
	@Autowired
	private EshopProductService eshopProductService;
	@Autowired
	private JedisPool jedisPool;
	
	@GetMapping("/product")
	public String getProduct(Long productId) {
		/*
			TODO 先读取本地EhCache，没有获取到数据再读取Redis主集群
		 */
		Jedis jedis = jedisPool.getResource();
		String dimProductJSON = jedis.get("dim_product_" + productId);
		/*
			Redis主集群没有获取到数据，则通过Feign调用依赖服务API
		 */
		if(dimProductJSON == null || "".equals(dimProductJSON)) {
	    	String productDataJSON = eshopProductService.findProductById(productId);
	    	if(productDataJSON != null && !"".equals(productDataJSON)) {
	    		JSONObject productDataJSONObject = JSONObject.parseObject(productDataJSON);
	    		String productPropertyDataJSON = eshopProductService.findProductPropertyByProductId(productId);
	    		if(productPropertyDataJSON != null && !"".equals(productPropertyDataJSON)) {
	    			productDataJSONObject.put("product_property", JSONObject.parse(productPropertyDataJSON));
	    		} 
	    		String productSpecificationDataJSON = eshopProductService.findProductSpecificationByProductId(productId);
	    		if(productSpecificationDataJSON != null && !"".equals(productSpecificationDataJSON)) {
	    			productDataJSONObject.put("product_specification", JSONObject.parse(productSpecificationDataJSON));
	    		}
	    		//更新数据到Redis主集群，通过复制更新到各机房从集群
	    		jedis.set("dim_product_" + productId, productDataJSONObject.toJSONString());

	    		//返回数据给Nginx
	    		return productDataJSONObject.toJSONString();
	    	} 
		}
		
		return "";
	}
}
