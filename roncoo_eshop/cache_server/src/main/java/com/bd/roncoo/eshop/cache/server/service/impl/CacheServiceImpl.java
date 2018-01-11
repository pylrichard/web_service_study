package com.bd.roncoo.eshop.cache.server.service.impl;

import com.bd.roncoo.eshop.cache.server.hystrix.command.GetProductInfoFromRedisCacheCommand;
import com.bd.roncoo.eshop.cache.server.hystrix.command.GetShopInfoFromRedisCacheCommand;
import com.bd.roncoo.eshop.cache.server.hystrix.command.SaveProductInfo2RedisCacheCommand;
import com.bd.roncoo.eshop.cache.server.hystrix.command.SaveShopInfo2RedisCacheCommand;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.bd.roncoo.eshop.cache.server.model.ShopInfo;
import com.bd.roncoo.eshop.cache.server.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("cacheService")
public class CacheServiceImpl implements CacheService {
    public static final String CACHE_NAME = "local";

    /**
     * 将商品信息保存到本地缓存中
     */
    @Override
    @CachePut(value = CACHE_NAME, key = "'key_' + #productInfo.getId()")
    public ProductInfo saveLocalCache(ProductInfo productInfo) {
        return productInfo;
    }

    /**
     * 从本地缓存中获取商品信息
     */
    @Override
    @Cacheable(value = CACHE_NAME, key = "'key_' + #id")
    public ProductInfo getLocalCache(Long id) {
        return null;
    }

    /**
     * 将商品信息保存到本地的EhCache缓存中
     */
    @Override
    @CachePut(value = CACHE_NAME, key = "'product_info_' + #productInfo.getId()")
    public ProductInfo saveProductInfo2LocalCache(ProductInfo productInfo) {
        return productInfo;
    }

    /**
     * 从本地EhCache缓存中获取商品信息
     */
    @Override
    @Cacheable(value = CACHE_NAME, key = "'product_info_' + #productId")
    public ProductInfo getProductInfoFromLocalCache(Long productId) {
        return null;
    }

    /**
     * 将店铺信息保存到本地的EhCache缓存中
     */
    @Override
    @CachePut(value = CACHE_NAME, key = "'shop_info_' + #shopInfo.getId()")
    public ShopInfo saveShopInfo2LocalCache(ShopInfo shopInfo) {
        return shopInfo;
    }

    /**
     * 从本地EhCache缓存中获取店铺信息
     */
    @Override
    @Cacheable(value = CACHE_NAME, key = "'shop_info_' + #shopId")
    public ShopInfo getShopInfoFromLocalCache(Long shopId) {
        return null;
    }

    /**
     * 将商品信息保存到Redis中
     */
    @Override
    public void saveProductInfo2RedisCache(ProductInfo productInfo) {
        SaveProductInfo2RedisCacheCommand command = new SaveProductInfo2RedisCacheCommand(productInfo);
        command.execute();
    }

    /**
     * 将店铺信息保存到Redis中
     */
    @Override
    public void saveShopInfo2RedisCache(ShopInfo shopInfo) {
        SaveShopInfo2RedisCacheCommand command = new SaveShopInfo2RedisCacheCommand(shopInfo);
        command.execute();
    }

    /**
     * 从Redis中获取商品信息
     */
    @Override
    public ProductInfo getProductInfoFromRedisCache(Long productId) {
        GetProductInfoFromRedisCacheCommand command = new GetProductInfoFromRedisCacheCommand(productId);

        return command.execute();
    }

    /**
     * 从Redis中获取店铺信息
     */
    @Override
    public ShopInfo getShopInfoFromRedisCache(Long shopId) {
        GetShopInfoFromRedisCacheCommand command = new GetShopInfoFromRedisCacheCommand(shopId);

        return command.execute();
    }
}
