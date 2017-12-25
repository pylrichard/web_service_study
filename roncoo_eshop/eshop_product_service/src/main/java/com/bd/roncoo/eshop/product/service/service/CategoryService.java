package com.bd.roncoo.eshop.product.service.service;

import com.bd.roncoo.eshop.product.service.model.Category;

public interface CategoryService {
	void add(Category category, String operationType);
	
	void update(Category category, String operationType);
	
	void delete(Long id, String operationType);
	
	Category findById(Long id);
}
