package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.common.http.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CircuitBreakerTest {
    private static Logger logger = LoggerFactory.getLogger(CircuitBreakerTest.class);

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 25; i++) {
            String response = HttpClientUtils.sendGetRequest("http://localhost:8081/getProductInfo?productId=-1");
            logger.info("第" + (i + 1) + "次请求，结果为：" + response);
        }
        /*
            Hystrix查看时间窗口内(比如最近5秒)异常数据是否达到一定比例，如果达到就执行短路
        */
        logger.info("等待5秒...");
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            String response = HttpClientUtils.sendGetRequest("http://localhost:8081/getProductInfo?productId=1");
            logger.info("第" + (i + 1) + "次请求，结果为：" + response);
        }
    }
}
