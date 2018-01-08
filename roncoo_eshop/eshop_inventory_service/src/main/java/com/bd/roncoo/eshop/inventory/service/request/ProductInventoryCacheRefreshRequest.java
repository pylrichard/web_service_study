package com.bd.roncoo.eshop.inventory.service.request;

import com.bd.roncoo.eshop.inventory.service.model.ProductInventory;
import com.bd.roncoo.eshop.inventory.service.service.ProductInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 刷新商品库存的缓存
 */
public class ProductInventoryCacheRefreshRequest implements Request {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Long productId;
    private ProductInventoryService productInventoryService;
    /**
     * 是否强制刷新缓存
     */
    private boolean forceRefresh;

    public ProductInventoryCacheRefreshRequest(Long productId,
                                               ProductInventoryService productInventoryService,
                                               boolean forceRefresh) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
        this.forceRefresh = forceRefresh;
    }

    @Override
    public void process() {
        //从数据库查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.findByProductId(productId);
        logger.info("已查询到商品最新的库存数量，商品id=" + productId
                + ", 商品库存数量=" + productInventory.getInventoryCnt());
        //将最新的商品库存数量刷新到Redis
        productInventoryService.setCache(productInventory);
    }

    @Override
    public Long getProductId() {
        return productId;
    }

    @Override
    public boolean isForceRefresh() {
        return forceRefresh;
    }
}
