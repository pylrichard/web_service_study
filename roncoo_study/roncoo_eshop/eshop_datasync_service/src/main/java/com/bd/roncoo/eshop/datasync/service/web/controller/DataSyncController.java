package com.bd.roncoo.eshop.datasync.service.web.controller;

import com.bd.roncoo.eshop.datasync.service.service.EshopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataSyncController {
	@Autowired
	private EshopProductService eshopProductService;
	
	@RequestMapping("/findBrandById")
	@ResponseBody
	public String findBrandById(Long id) {
		return eshopProductService.findBrandById(id);
	}
}
