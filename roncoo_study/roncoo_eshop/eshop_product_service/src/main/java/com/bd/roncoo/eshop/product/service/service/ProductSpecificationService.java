package com.bd.roncoo.eshop.product.service.service;

import com.bd.roncoo.eshop.product.service.model.ProductSpecification;

public interface ProductSpecificationService {
	void add(ProductSpecification productSpecification, String operationType);
	
	void update(ProductSpecification productSpecification, String operationType);
	
	void delete(Long id, String operationType);
	
	ProductSpecification findById(Long id);
	
	ProductSpecification findByProductId(Long productId);
}
