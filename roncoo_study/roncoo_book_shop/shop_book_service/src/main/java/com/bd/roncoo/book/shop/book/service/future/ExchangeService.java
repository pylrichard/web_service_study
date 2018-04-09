package com.bd.roncoo.book.shop.book.service.future;

import java.util.Random;

/**
 * 汇率查询服务
 */
public class ExchangeService {
    public static double getRate(String source, String target) {
        delay();

        return 10;
    }

    public static void delay() {
        int delay = 500 + new Random().nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
