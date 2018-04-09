package com.bd.roncoo.eshop.one.service.service;

import com.bd.roncoo.eshop.one.service.service.fallback.EshopPriceServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "eshop_price_service", fallback = EshopPriceServiceFallback.class)
public interface EshopPriceService {
    @GetMapping(value = "/product-price/findByProductId")
    String findByProductId(@RequestParam(value = "productId") Long productId);
}