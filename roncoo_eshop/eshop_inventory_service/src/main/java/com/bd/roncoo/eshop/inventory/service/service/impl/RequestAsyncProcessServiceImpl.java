package com.bd.roncoo.eshop.inventory.service.service.impl;

import com.bd.roncoo.eshop.inventory.service.request.Request;
import com.bd.roncoo.eshop.inventory.service.request.RequestQueue;
import com.bd.roncoo.eshop.inventory.service.service.RequestAsyncProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void process(Request request) {
        try {
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
