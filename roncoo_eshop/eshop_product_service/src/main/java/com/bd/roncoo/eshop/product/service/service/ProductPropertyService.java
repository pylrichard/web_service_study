package com.bd.roncoo.eshop.product.service.service;

import com.bd.roncoo.eshop.product.service.model.ProductProperty;

public interface ProductPropertyService {
	void add(ProductProperty productProperty, String operationType);
	
	void update(ProductProperty productProperty, String operationType);
	
	void delete(Long id, String operationType);
	
	ProductProperty findById(Long id);
	
	ProductProperty findByProductId(Long productId);
}
