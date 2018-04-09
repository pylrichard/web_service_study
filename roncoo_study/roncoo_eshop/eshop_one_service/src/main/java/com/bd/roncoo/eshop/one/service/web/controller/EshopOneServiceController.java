package com.bd.roncoo.eshop.one.service.web.controller;

import com.bd.roncoo.eshop.one.service.service.EshopInventoryService;
import com.bd.roncoo.eshop.one.service.service.EshopPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/one")  
public class EshopOneServiceController {
	@Autowired
	private EshopInventoryService eshopInventoryService;
	@Autowired
	private EshopPriceService eshopPriceService;
	
	@RequestMapping("/findInventoryByProductId")
	public String findInventoryByProductId(Long productId) {
		return eshopInventoryService.findByProductId(productId);
	}
	
	@RequestMapping("/findPriceByProductId")
	public String findPriceByProductId(Long productId) {
		return eshopPriceService.findByProductId(productId);
	}
}
