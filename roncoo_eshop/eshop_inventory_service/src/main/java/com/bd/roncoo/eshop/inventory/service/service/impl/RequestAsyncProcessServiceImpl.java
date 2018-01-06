package com.bd.roncoo.eshop.inventory.service.service.impl;

import com.bd.roncoo.eshop.inventory.service.request.ProductInventoryCacheRefreshRequest;
import com.bd.roncoo.eshop.inventory.service.request.ProductInventoryDbUpdateRequest;
import com.bd.roncoo.eshop.inventory.service.request.Request;
import com.bd.roncoo.eshop.inventory.service.request.RequestQueue;
import com.bd.roncoo.eshop.inventory.service.service.RequestAsyncProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void process(Request request) {
        try {
            //读请求去重
            RequestQueue requestQueue = RequestQueue.getInstance();
            Map<Long, Boolean> flagMap = requestQueue.getFlagMap();
            if (request instanceof ProductInventoryDbUpdateRequest) {
                //如果是数据库更新请求，将productId对应的标识设置为true
                flagMap.put(request.getProductId(), true);
            } else if (request instanceof ProductInventoryCacheRefreshRequest) {
                Boolean flag = flagMap.get(request.getProductId());
                if (flag == null) {
                    flagMap.put(request.getProductId(), false);
                }
                //如果是缓存刷新请求，判断标识不为空而且是true，说明之前有一个数据库更新请求
                if (flag != null && flag) {
                    flagMap.put(request.getProductId(), false);
                }
                //判断标识不为空而且是false，说明之前有一个数据库更新请求+一个缓存刷新请求
                if (flag != null && !flag) {
                    //对于读请求就过滤，不要路由到内存队列
                    return;
                }
            }
            /*
                根据每个请求的商品id，将请求路由到对应的内存队列
             */
            ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getProductId());
            queue.put(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取路由内存队列
     */
    private ArrayBlockingQueue<Request> getRoutingQueue(Long productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        /*
            获取productId的hash值
         */
        String key = String.valueOf(productId);
        int value;
        int hashCode = (key == null) ? 0 : (value = key.hashCode()) ^ (value >>> 16);
        /*
            对Hash值取模，将Hash值路由到指定的内存队列
            用内存队列大小(比如8)对Hash值取模后，结果在0~7之间
            所以任何一个商品id都会被固定路由到同一个内存队列
        */
        int index = (requestQueue.queueSize() - 1) & hashCode;
        logger.info("路由内存队列，商品id=" + productId + ", 队列索引=" + index);

        return requestQueue.getQueue(index);
    }
}
