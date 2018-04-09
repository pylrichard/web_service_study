package com.bd.roncoo.eshop.inventory.service.service;

import com.bd.roncoo.eshop.inventory.service.model.ProductInventory;

public interface ProductInventoryService {
	void add(ProductInventory productInventory);
	
	void update(ProductInventory productInventory);
	
	void delete(Long id);

    void setCache(ProductInventory productInventory);

    void deleteCache(ProductInventory productInventory);

    ProductInventory getCache(Long productId);

	ProductInventory findById(Long id);
	
	ProductInventory findByProductId(Long productId);
}
