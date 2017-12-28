package com.bd.roncoo.eshop.one.service.service;

import com.bd.roncoo.eshop.one.service.service.fallback.EshopInventoryServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "eshop_inventory_service", fallback = EshopInventoryServiceFallback.class)
public interface EshopInventoryService {
    @GetMapping("/product-inventory/findByProductId")
    String findByProductId(@RequestParam(value = "productId") Long productId);
}