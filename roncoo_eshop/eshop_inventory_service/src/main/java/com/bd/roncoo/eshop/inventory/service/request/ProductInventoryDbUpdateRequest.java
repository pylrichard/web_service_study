package com.bd.roncoo.eshop.inventory.service.request;

import com.bd.roncoo.eshop.inventory.service.model.ProductInventory;
import com.bd.roncoo.eshop.inventory.service.service.ProductInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductInventoryDbUpdateRequest implements Request {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ProductInventory productInventory;
    private ProductInventoryService productInventoryService;

    public ProductInventoryDbUpdateRequest(ProductInventory productInventory,
                                           ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        logger.info("数据库更新请求开始执行，商品id=" + productInventory.getProductId()
                + ", 商品库存数量=" + productInventory.getInventoryCnt());
        //删除Redis中的缓存
        productInventoryService.deleteCache(productInventory);
        //模拟先删除Redis中的缓存，还没更新数据库中的库存，此时发送过来一个读请求
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //修改数据库中的库存
        productInventoryService.update(productInventory);
    }

    /**
     * 获取商品id
     */
    @Override
    public Long getProductId() {
        return productInventory.getProductId();
    }
}
