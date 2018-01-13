package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.common.http.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 见96-深入理解Hystrix的短路器执行原理以及模拟接口异常时的短路实验
 */
public class CircuitBreakerTest {
    private static Logger logger = LoggerFactory.getLogger(CircuitBreakerTest.class);

    public static void main(String[] args) throws Exception {
        String request = "http://localhost:8081/getProductInfo?productId=";
        String response = null;
        /*
            Hystrix查看时间窗口内(比如最近10秒)异常请求占比是否达到阈值，如果达到就开启短路
        */
        for (int i = 0; i < 60; i++) {
            //发送正常请求
            response = HttpClientUtils.sendGetRequest(request + "1");
            logger.info("第" + (i + 1) + "次请求，结果为：" + response);
        }
        Thread.sleep(9000);
        for (int i = 0; i < 40; i++) {
            //发送异常请求
            response = HttpClientUtils.sendGetRequest(request + "-1");
            logger.info("第" + (i + 1) + "次请求，结果为：" + response);
        }
        /*
            一段时间后进入half-open状态，让一个请求经过短路器，看能不能正常调用。如果调用成功，就自动恢复，转换到close状态
         */
        logger.info("等待5秒...");
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            response = HttpClientUtils.sendGetRequest(request + "1");
            logger.info("第" + (i + 1) + "次请求，结果为：" + response);
        }
    }
}
