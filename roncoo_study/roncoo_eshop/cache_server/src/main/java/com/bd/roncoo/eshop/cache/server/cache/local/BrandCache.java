package com.bd.roncoo.eshop.cache.server.cache.local;

import java.util.HashMap;
import java.util.Map;

/**
 * 品牌缓存
 */
public class BrandCache {
    private static Map<Long, String> brandMap = new HashMap<>();
    private static Map<Long, Long> productBrandMap = new HashMap<>();

    static {
        brandMap.put(1L, "iPhone");
        productBrandMap.put(-1L, 1L);
    }

    public static String getBrandName(Long brandId) {
        return brandMap.get(brandId);
    }

    public static Long getBrandId(Long productId) {
        return productBrandMap.get(productId);
    }
}
