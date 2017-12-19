package com.bd.roncoo.book.shop.book.service.future;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * 商店商品价格查询服务
 */
@Getter
@Setter
public class ShopService {
    public String name;
    Random random = new Random();

    public ShopService(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws Exception {
        ShopService shop = new ShopService("shop1");
        long start = System.currentTimeMillis();
        Future<Quote> futurePrice = shop.getPriceAsync("MacBook Pro");
        System.out.println("time1: " + (System.currentTimeMillis() - start));
        Quote price = futurePrice.get();
        System.out.println("time2: " + (System.currentTimeMillis() - start));
    }

    public static void delay() {
        int delay = 500 + new Random().nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    public String getPrice(String product) {
        delay();
        double price = random.nextDouble() * 100;
        Discount discount = Discount.values()[random.nextInt(Discount.values().length)];

        return String.format("%s:%.2f:%s", getName(), price, discount);
    }
    */

    public Quote getPrice(String product) {
        delay();
        double price = random.nextDouble() * 100;
        Discount discount = Discount.values()[random.nextInt(Discount.values().length)];

        return new Quote(getName(), price, discount);
    }

    public Future<Quote> getPriceAsync(String product) {
//        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
//        new Thread(()->futurePrice.complete(getPrice(product))).start();
//
//        return futurePrice;

        //将同步方法转换为异步方法，supplyAsync()内部会对参数lambda方法(()->getPrice(product))产生的异常进行处理，避免线程因为异常而阻塞
        return CompletableFuture.supplyAsync(() -> getPrice(product));
    }
}
