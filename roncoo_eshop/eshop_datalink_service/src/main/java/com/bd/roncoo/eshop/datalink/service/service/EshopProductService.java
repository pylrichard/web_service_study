package com.bd.roncoo.eshop.datalink.service.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "eshop_product_service")
public interface EshopProductService {
    @GetMapping("/brand/findById")
    String findBrandById(@RequestParam(value = "id") Long id);
    
    @GetMapping("/brand/findByIds")
    String findBrandByIds(@RequestParam(value = "ids") String ids);
    
    @GetMapping("/category/findById")
    String findCategoryById(@RequestParam(value = "id") Long id);
    
    @GetMapping("/product-intro/findById")
    String findProductIntroById(@RequestParam(value = "id") Long id);
    
    @GetMapping("/product-property/findById")
    String findProductPropertyById(@RequestParam(value = "id") Long id);
    
    @GetMapping("/product-property/findByProductId")
    String findProductPropertyByProductId(@RequestParam(value = "productId") Long productId);
    
    @GetMapping("/product/findById")
    String findProductById(@RequestParam(value = "id") Long id);
    
    @GetMapping("/product-specification/findById")
    String findProductSpecificationById(@RequestParam(value = "id") Long id);
    
    @GetMapping("/product-specification/findByProductId")
    String findProductSpecificationByProductId(@RequestParam(value = "productId") Long productId);
}