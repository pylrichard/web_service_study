package com.bd.roncoo.eshop.cache.server.service;

import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.bd.roncoo.eshop.cache.server.model.ShopInfo;

public interface CacheService {
    /**
     * 将商品信息保存到本地缓存中
     */
    ProductInfo saveLocalCache(ProductInfo productInfo);

    /**
     * 从本地缓存中获取商品信息
     */
    ProductInfo getLocalCache(Long id);

    /**
     * 将商品信息保存到本地的EhCache缓存中
     */
    ProductInfo saveProductInfo2LocalCache(ProductInfo productInfo);

    /**
     * 从本地EhCache缓存中获取商品信息
     */
    ProductInfo getProductInfoFromLocalCache(Long productId);

    /**
     * 将店铺信息保存到本地的EhCache缓存中
     */
    ShopInfo saveShopInfo2LocalCache(ShopInfo shopInfo);

    /**
     * 从本地EhCache缓存中获取店铺信息
     */
    ShopInfo getShopInfoFromLocalCache(Long shopId);

    /**
     * 将商品信息保存到Redis中
     */
    void saveProductInfo2RedisCache(ProductInfo productInfo);

    /**
     * 将店铺信息保存到Redis中
     */
    void saveShopInfo2RedisCache(ShopInfo shopInfo);

    /**
     * 从Redis中获取商品信息
     */
    ProductInfo getProductInfoFromRedisCache(Long productId);

    /**
     * 从Redis中获取店铺信息
     */
    ShopInfo getShopInfoFromRedisCache(Long shopId);
}
