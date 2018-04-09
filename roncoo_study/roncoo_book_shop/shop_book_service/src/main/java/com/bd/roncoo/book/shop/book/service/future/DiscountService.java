package com.bd.roncoo.book.shop.book.service.future;

import java.text.NumberFormat;
import java.util.Random;

/**
 * 折扣查询服务
 */
public class DiscountService {
    public static void delay() {
        int delay = 500 + new Random().nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShop() + " price is " + apply(quote.getPrice(), quote.getDiscount());
    }

    private static String apply(double price, Discount discount) {
        delay();
        return NumberFormat.getInstance().format(price * (100 - discount.getPercent()) / 100);
    }
}
