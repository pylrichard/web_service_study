package com.bd.roncoo.eshop.product.service.service;

import com.bd.roncoo.eshop.product.service.model.Product;

public interface ProductService {
	void add(Product product, String operationType);
	
	void update(Product product, String operationType);
	
	void delete(Long id, String operationType);
	
	Product findById(Long id);
}
