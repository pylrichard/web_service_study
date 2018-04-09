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
     * 1）写请求(数据库更新请求)会先删除Redis中的缓存，然后模拟卡顿2s(见ProductInventoryDbUpdateRequest.process())
     * 2）在卡顿的2s内发送一个读请求(缓存更新请求)，此时Redis中没有相应的缓存，将读请求路由到同一个内存队列中排队等待
     * 3）5s后写请求完成数据库更新，读请求才会执行
     * 4）读请求执行时会将最新的库存从数据库更新到缓存
     * <p>
     * 如果出现不一致情况，可能出现Redis中库存为100，但是数据库中库存更新为99，所以一致性保障方案保证数据一致性
     */
    @RequestMapping("/update")
    public Response update(ProductInventory productInventory) {
        logger.info("接收到更新商品库存的请求，商品id=" + productInventory.getProductId()
                + ", 商品库存数量=" + productInventory.getInventoryCnt());
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
        logger.info("接收到商品库存的读请求，商品id=" + productId);
        ProductInventory productInventory = null;
        try {
            Request request = new ProductInventoryCacheRefreshRequest(productId,
                    productInventoryService, false);
            requestAsyncProcessService.process(request);
            /*
                将读请求(缓存更新请求)交给Service异步处理后，同步等待可能并发执行的写请求(数据库更新请求)完成，再从Redis获取数据
            */
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;
            while (true) {
                //生产环境读请求等待时间控制在200ms，开发/测试环境可以调大
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
                /*
                    如果没有读取到结果则等待
                    包含2种情况:
                    1 数据库里没有相应的数据，缓存肯定也没有
                    2 数据库更新操作先删除了相应缓存
                 */
                else {
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }
            //等待超过200ms没有从缓存获取到结果，从数据库中读取数据
            productInventory = productInventoryService.findByProductId(productId);
            if (productInventory != null) {
                //此处刷新缓存是一个读操作，如果没有放在队列中串行执行而直接调用setCache()，会有数据不一致问题
                request = new ProductInventoryCacheRefreshRequest(productId,
                        productInventoryService, true);
                requestAsyncProcessService.process(request);
                /*
                    代码运行到这里有3种情况:
				    1 上一次执行的也是读请求(缓存更新请求)，数据刷新到Redis，但是被Redis的LRU算法清理掉了，标志位还是false
				    所以此时下一个读请求(缓存更新请求，forceRefresh = false)从缓存中获取不到数据
				    添加一个读请求(缓存更新请求，forceRefresh = true)到内存队列中强制刷新数据到缓存
				    2 可能在200ms内读请求(缓存更新请求)在队列中一直等待，并未执行，需要从数据库获取数据，然后刷新缓存
				    3 缓存中本来就没有对应的数据，从数据库中获取
                 */

                return productInventory;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ProductInventory(productId, -1L);
    }
}
