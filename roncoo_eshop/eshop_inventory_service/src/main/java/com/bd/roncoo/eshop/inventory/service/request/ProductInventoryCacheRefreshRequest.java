package com.bd.roncoo.eshop.inventory.service.request;

import com.bd.roncoo.eshop.inventory.service.model.ProductInventory;
import com.bd.roncoo.eshop.inventory.service.service.ProductInventoryService;

/**
 * 刷新商品库存的缓存
 */
public class ProductInventoryCacheRefreshRequest implements Request {
    private Long productId;
    private ProductInventoryService productInventoryService;

    public ProductInventoryCacheRefreshRequest(Long productId,
                                               ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        //从数据库查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.findByProductId(productId);
        //将最新的商品库存数量刷新到Redis
        productInventoryService.setCache(productInventory);
    }

    @Override
    public Long getProductId() {
        return productId;
    }
}
