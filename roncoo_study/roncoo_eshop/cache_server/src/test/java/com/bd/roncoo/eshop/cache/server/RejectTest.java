package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.common.http.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 见97-深入理解线程池隔离技术的设计原则以及动手实战接口限流实验
 */
public class RejectTest {
    private static Logger logger = LoggerFactory.getLogger(RejectTest.class);

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 25; i++) {
            new TestThread(i).start();
        }
    }

    private static class TestThread extends Thread {
        private int index;

        public TestThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            String request = "http://localhost:8081/getProductInfo?productId=";
            String response = HttpClientUtils.sendGetRequest(request + "-2");
            logger.info("第" + (index + 1) + "次请求，结果为：" + response);
        }
    }
}
