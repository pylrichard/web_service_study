package com.bd.roncoo.eshop.product.service.web.controller;

import com.bd.roncoo.eshop.product.service.model.Brand;
import com.bd.roncoo.eshop.product.service.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
	@Autowired
	private BrandService brandService;
	
	@RequestMapping("/add") 
	public String add(Brand brand, String operationType) {
		try {
			brandService.add(brand, operationType);
		} catch (Exception e) {
			e.printStackTrace(); 
			return "error";
		}
		return "success";
	}
	
	@RequestMapping("/update") 
	public String update(Brand brand, String operationType) {
		try {
			brandService.update(brand, operationType); 
		} catch (Exception e) {
			e.printStackTrace(); 
			return "error";
		}
		return "success";
	}
	
	@RequestMapping("/delete") 
	public String delete(Long id, String operationType) {
		try {
			brandService.delete(id, operationType); 
		} catch (Exception e) {
			e.printStackTrace(); 
			return "error";
		}
		return "success";
	}
	
	@RequestMapping("/findById") 
	public Brand findById(Long id){
		try {
			return brandService.findById(id);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return new Brand();
	}
	
	@RequestMapping("/findByIds") 
	public List<Brand> findByIds(String ids){
		try {
			return brandService.findByIds(ids);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return new ArrayList<Brand>();
	}
}
