package com.bd.roncoo.eshop.datasync.service.service;

import com.bd.roncoo.eshop.datasync.service.service.fallback.EshopProductServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 通过Feign调用商品服务获取商品数据
 */
@FeignClient(value = "eshop_product_service", fallback = EshopProductServiceFallback.class)
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
    
    @GetMapping("/product/findById")
    String findProductById(@RequestParam(value = "id") Long id);
    
    @GetMapping("/product-specification/findById")
    String findProductSpecificationById(@RequestParam(value = "id") Long id);
}