package com.bd.roncoo.eshop.inventory.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.inventory.service.mapper.ProductInventoryMapper;
import com.bd.roncoo.eshop.inventory.service.model.ProductInventory;
import com.bd.roncoo.eshop.inventory.service.service.ProductInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ProductInventoryMapper productInventoryMapper;
	@Autowired
	private JedisPool jedisPool;
	
	@Override
	public void add(ProductInventory productInventory) {
		productInventoryMapper.add(productInventory);
        setCache(productInventory);
    }
	
	@Override
	public void update(ProductInventory productInventory) {
		productInventoryMapper.update(productInventory);
        setCache(productInventory);
		logger.info("已修改数据库中的库存，商品id=" + productInventory.getProductId()
				+ ", 商品库存数量=" + productInventory.getInventoryCnt());
	}

	@Override
    public void delete(Long id) {
        ProductInventory productInventory = findById(id);
        productInventoryMapper.delete(id);
        deleteCache(productInventory);
    }

    @Override
    public void setCache(ProductInventory productInventory) {
        Jedis jedis = jedisPool.getResource();
		String key = "product_inventory_" + productInventory.getProductId();
		jedis.set(key, JSONObject.toJSONString(productInventory));
		logger.info("已更新商品库存的缓存，商品id=" + productInventory.getProductId()
				+ ", 商品库存数量=" + productInventory.getInventoryCnt() + ", key=" + key);
	}

    @Override
    public ProductInventory getCache(Long productId) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get("product_inventory_" + productId);
        if (result != null && !"".equals(result)) {
            return (ProductInventory) JSONObject.parse(result);
        }

        return null;
    }

    @Override
    public void deleteCache(ProductInventory productInventory) {
        Jedis jedis = jedisPool.getResource();
		String key = "product_inventory_" + productInventory.getProductId();
		jedis.del(key);
		logger.info("已删除Redis中的缓存，key=" + key);
	}

	@Override
	public ProductInventory findById(Long id) {
		return productInventoryMapper.findById(id);
	}

	/**
	 * OneService调用此API，获取实时库存
	 */
	@Override
	public ProductInventory findByProductId(Long productId) {
		Jedis jedis = jedisPool.getResource();
		String dataJSON = jedis.get("product_inventory_" + productId);
		if(dataJSON != null && !"".equals(dataJSON)) {
			JSONObject dataJSONObject = JSONObject.parseObject(dataJSON);
			//模拟主键
			dataJSONObject.put("id", "-1");
			return JSONObject.parseObject(dataJSONObject.toJSONString(), ProductInventory.class);
		} else {
			//Redis中没有获取到数据，查询MySQL
			return productInventoryMapper.findByProductId(productId);
		}
	}
}
