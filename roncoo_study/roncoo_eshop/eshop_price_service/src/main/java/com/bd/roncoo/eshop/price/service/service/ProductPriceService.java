package com.bd.roncoo.eshop.price.service.service;

import com.bd.roncoo.eshop.price.service.model.ProductPrice;

public interface ProductPriceService {
	void add(ProductPrice productPrice);
	
	void update(ProductPrice productPrice);
	
	void delete(Long id);
	
	ProductPrice findById(Long id);
	
	ProductPrice findByProductId(Long id);
}
