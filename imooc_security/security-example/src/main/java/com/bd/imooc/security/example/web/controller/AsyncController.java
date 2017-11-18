package com.bd.imooc.security.example.web.controller;

import com.bd.imooc.security.example.web.async.DeferredResultHolder;
import com.bd.imooc.security.example.web.async.MockQueue;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/order")
public class AsyncController {
    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 有3个线程协同完成下单流程
     * 主线程，MockQueue里的线程，QueueListener里的线程
     */
    @GetMapping("/deferred_result")
    public DeferredResult<String> orderWithDeferredResult() throws Exception {
        logger.info("主线程开始");
        //生成订单号
        String orderNumber = RandomStringUtils.randomNumeric(8);
        //1 发送下单消息
        mockQueue.setPlaceOrder(orderNumber);
        DeferredResult<String> result = new DeferredResult<>();
        //填充订单号和响应结果
        deferredResultHolder.getMap().put(orderNumber, result);
        logger.info("主线程返回");

        //4 返回响应给前端
        return result;
    }

    @GetMapping("/callable")
    public String orderWithCallable() throws Exception {
        long start = System.currentTimeMillis();
        logger.info("主线程开始");
        /*
            副线程代码在主线程代码中，Callable无法处理复杂逻辑
         */
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始");
                Thread.sleep(1000);
                logger.info("副线程返回，耗时:" + (System.currentTimeMillis() - start));
                return "hello";
            }
        };
        String str = result.call();
        logger.info("主线程返回,耗时:" + (System.currentTimeMillis() - start));

        return str;
    }
}
