package com.bd.roncoo.eshop.inventory.service.web.controller;

import com.bd.roncoo.eshop.inventory.service.model.ProductInventory;
import com.bd.roncoo.eshop.inventory.service.request.ProductInventoryCacheRefreshRequest;
import com.bd.roncoo.eshop.inventory.service.request.ProductInventoryDbUpdateRequest;
import com.bd.roncoo.eshop.inventory.service.request.Request;
import com.bd.roncoo.eshop.inventory.service.service.ProductInventoryService;
import com.bd.roncoo.eshop.inventory.service.service.RequestAsyncProcessService;
import com.bd.roncoo.eshop.inventory.service.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-inventory")
public class ProductInventoryController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductInventoryService productInventoryService;
    @Autowired
    private RequestAsyncProcessService requestAsyncProcessService;

    @RequestMapping("/add")
    public String add(ProductInventory productInventory) {
        try {
            productInventoryService.add(productInventory);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    /**
     * 模拟的场景：
     * 1）商品库存的更新请求会先删除Redis中的缓存，然后模拟卡顿5s
     * 2）在卡顿的5s内发送一个商品缓存的读请求，此时Redis中没有相应的缓存，将读请求路由到同一个内存队列中排队等待
     * 3）5s后更新请求完成数据库更新，读请求才会执行
     * 4）读请求执行时会将最新的库存从数据库更新到缓存
     * <p>
     * 如果出现不一致情况，可能出现Redis中库存为100，但是数据库中库存更新为99，所以一致性保障方案保证数据一致性
     */
    @RequestMapping("/update")
    public Response update(ProductInventory productInventory) {
        Response response = null;
        try {
            Request request = new ProductInventoryDbUpdateRequest(
                    productInventory, productInventoryService);
            requestAsyncProcessService.process(request);
            response = new Response(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Response.FAILURE);
        }

        return response;
    }

    @RequestMapping("/delete")
    public String delete(Long id) {
        try {
            productInventoryService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @RequestMapping("/findById")
    public ProductInventory findById(Long id) {
        try {
            return productInventoryService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory();
    }

    @RequestMapping("/findByProductId")
    public ProductInventory findByProductId(Long productId) {
        ProductInventory productInventory = null;
        try {
            Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService);
            requestAsyncProcessService.process(request);
            //将请求扔给service异步去处理以后，就需要while(true)一会儿，在这里hang住
            // 去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;
            //等待超过200ms没有从缓存获取到结果
            while (true) {
                //读请求等待时间控制在200ms
                if (waitTime > 200) {
                    break;
                }
                //从Redis中读取商品库存的缓存
                productInventory = productInventoryService.getCache(productId);
                //如果读取到结果就返回
                if (productInventory != null) {
                    logger.info("200ms内读取到Redis中的库存缓存，商品id=" + productInventory.getProductId()
                            + ", 商品库存数量=" + productInventory.getInventoryCnt());
                    return productInventory;
                }
                //如果没有读取到结果则等待
                else {
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }
            //从数据库中读取数据
            productInventory = productInventoryService.findByProductId(productId);
            if (productInventory != null) {
                //将数据库中的最新库存刷新到缓存
                productInventoryService.setCache(productInventory);

                return productInventory;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ProductInventory(productId, -1L);
    }
}
