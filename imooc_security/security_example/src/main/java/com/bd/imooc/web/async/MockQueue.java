package com.bd.imooc.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 模拟监听消息队列的下单消息，处理下单逻辑，返回订单完成消息，属于后端独立应用
 */
@Component
public class MockQueue {
    /**
     * 下单消息
     */
    private String placeOrder;
    /**
     * 订单完成消息
     */
    private String completeOrder;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) throws Exception {
        new Thread(() -> {
            logger.info("接到下单请求, " + placeOrder);
            try {
                //2 模拟下单处理逻辑
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder;
            logger.info("下单请求处理完毕," + placeOrder);
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
