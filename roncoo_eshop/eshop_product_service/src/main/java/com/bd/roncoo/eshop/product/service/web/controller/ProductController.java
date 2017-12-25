package com.bd.roncoo.eshop.product.service.web.controller;

import com.bd.roncoo.eshop.product.service.model.Product;
import com.bd.roncoo.eshop.product.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/add") 
	public String add(Product product, String operationType) {
		try {
			productService.add(product, operationType);
		} catch (Exception e) {
			e.printStackTrace(); 
			return "error";
		}
		return "success";
	}
	
	@RequestMapping("/update") 
	public String update(Product product, String operationType) {
		try {
			productService.update(product, operationType); 
		} catch (Exception e) {
			e.printStackTrace(); 
			return "error";
		}
		return "success";
	}
	
	@RequestMapping("/delete") 
	public String delete(Long id, String operationType) {
		try {
			productService.delete(id, operationType); 
		} catch (Exception e) {
			e.printStackTrace(); 
			return "error";
		}
		return "success";
	}
	
	@RequestMapping("/findById") 
	public Product findById(Long id){
		try {
			return productService.findById(id);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return new Product();
	}
}
