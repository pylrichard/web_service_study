package com.bd.roncoo.eshop.product.service.web.controller;

import com.bd.roncoo.eshop.product.service.model.ProductSpecification;
import com.bd.roncoo.eshop.product.service.service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-specification")
public class ProductSpecificationController {
	@Autowired
	private ProductSpecificationService productSpecificationService;
	
	@RequestMapping("/add") 
	public String add(ProductSpecification productSpecification, String operationType) {
		try {
			productSpecificationService.add(productSpecification, operationType);
		} catch (Exception e) {
			e.printStackTrace(); 
			return "error";
		}
		return "success";
	}
	
	@RequestMapping("/update") 
	public String update(ProductSpecification productSpecification, String operationType) {
		try {
			productSpecificationService.update(productSpecification, operationType); 
		} catch (Exception e) {
			e.printStackTrace(); 
			return "error";
		}
		return "success";
	}
	
	@RequestMapping("/delete") 
	public String delete(Long id, String operationType) {
		try {
			productSpecificationService.delete(id, operationType); 
		} catch (Exception e) {
			e.printStackTrace(); 
			return "error";
		}
		return "success";
	}
	
	@RequestMapping("/findById") 
	public ProductSpecification findById(Long id){
		try {
			return productSpecificationService.findById(id);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return new ProductSpecification();
	}
	
	@RequestMapping("/findByProductId") 
	public ProductSpecification findByProductId(Long productId){
		try {
			return productSpecificationService.findByProductId(productId);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return new ProductSpecification();
	}
}
