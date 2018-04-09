package com.bd.roncoo.eshop.product.service.service;

import com.bd.roncoo.eshop.product.service.model.Brand;

import java.util.List;

public interface BrandService {
	void add(Brand brand, String operationType);
	
	void update(Brand brand, String operationType);
	
	void delete(Long id, String operationType);
	
	Brand findById(Long id);
	
	List<Brand> findByIds(String ids);
}
