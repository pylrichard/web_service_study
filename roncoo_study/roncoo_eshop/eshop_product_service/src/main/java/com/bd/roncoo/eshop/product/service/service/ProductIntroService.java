package com.bd.roncoo.eshop.product.service.service;

import com.bd.roncoo.eshop.product.service.model.ProductIntro;

public interface ProductIntroService {
	void add(ProductIntro productIntro, String operationType);
	
	void update(ProductIntro productIntro, String operationType);
	
	void delete(Long id, String operationType);
	
	ProductIntro findById(Long id);
}
