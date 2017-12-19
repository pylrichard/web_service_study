package com.bd.roncoo.book.shop.book.service.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 商品价格查询服务
 */
public class PriceService {
    private List<ShopService> shops = Arrays.asList(new ShopService("shop1"), new ShopService("shop2"),
            new ShopService("shop3"), new ShopService("shop4"),
            new ShopService("shop5"), new ShopService("shop6"),
            new ShopService("shop7"), new ShopService("shop8"),
            new ShopService("shop9"));

    public static void main(String[] args) {
        PriceService price = new PriceService();
        long start = System.currentTimeMillis();
//        System.out.println(price.findPrices("MacBook Pro"));
        price.findPrices("MacBook Pro");
        System.out.println("time: " + (System.currentTimeMillis() - start) + " ms");
    }

    /*
    public List<String> findPrices(String product) {
        //同步
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }
    */

    /*
    public List<String> findPrices(String product) {
        //异步，4个shop耗时1s，5个shop耗时2s，因为CPU是4核，可以并发执行4个线程(耗时1s)，第5个shop要等待
        //9个shop耗时3s
        return shops.stream().parallel()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }
    */

    /*
    public List<String> findPrices(String product) {
        //产生2个stream进行并行，如果只有1个stream，supplyAsync()执行后join()进行阻塞等待，则变为同步
        //CompletableFuture可以指定线程池(此处指定与shop数量相同的线程数)，而parallel()不可以
        //计算密集型应用加大线程数量并不会提升效率，而IO密集型应用(等待远程服务返回结果)加大线程数可以提升效率
        //比如购物车有10件商品，查询商品价格使用CompletableFuture+线程池可大幅提升效率
        //
        //线程池中线程数目T = N * U * (1 + W/C)
        //N是CPU核数，通过Runtime.getRuntime().availableProcessors()获取
        //U是预期CPU利用率，范围为0~1
        //W是等待时间，C是计算时间
        //getPrice()执行耗时1100ms，等待时间为1000ms，执行时间为100ms，W/C = 10
        Executor executor = Executors.newFixedThreadPool(Math.min(shops.size() * 2, 100));
        List<CompletableFuture<String>> priceFuture = shops.stream()
                //异步，获取每个shop的名称、价格、折扣
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                //同步，将上一个任务返回的String解析成Quote
                .map(future -> future.thenApply(Quote::parse))
                //异步，thenCompose连接2个异步任务，下一个任务的输入是上一个任务的输出
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> DiscountService.applyDiscount(quote), executor)))
                .collect(Collectors.toList());

        //CompletableFuture::join进行阻塞等待，不会抛出异常
        return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
    */

    /*
    public List<String> findPrices(String product) {
        Executor executor = Executors.newFixedThreadPool(Math.min(shops.size() * 2, 100));
        List<CompletableFuture<String>> priceFuture = shops.stream()
                //异步，获取每个shop的名称、价格、折扣
                //thenCombine同时执行2个异步任务，并将2个异步任务的结果进行合并，线程池大小设置为shops.size() * 2
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor).thenCombine(
                        CompletableFuture.supplyAsync(() -> ExchangeService.getRate("USD", "CNY"), executor),
                        //quote是第1个异步任务的返回结果，rate是第2个异步任务的返回结果
                        (quote, rate) -> new Quote(quote.getShop(), quote.getPrice() * rate, quote.getDiscount())))
                //异步，thenCompose连接2个异步任务，下一个任务的输入是上一个任务的输出
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> DiscountService.applyDiscount(quote), executor)))
                //合并所有线程(所有商店)的返回结果
                .collect(Collectors.toList());

        //CompletableFuture::join进行阻塞等待，不会抛出异常
        return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
    */

    /*
    public void findPrices(String product) {
        Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100));
        CompletableFuture<?>[] priceFuture = shops.stream()
                //异步，获取每个shop的名称、价格、折扣
                //thenCombine同时执行2个异步任务，并将2个异步任务的结果进行合并，线程池大小设置为shops.size() * 2
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor)
                        .thenCombine(
                            CompletableFuture.supplyAsync(() -> ExchangeService.getRate("USD", "CNY"), executor),
                            //quote是第1个异步任务的返回结果，rate是第2个异步任务的返回结果
                            (quote, rate) -> new Quote(quote.getShop(), quote.getPrice() * rate, quote.getDiscount())
                        )
                )
                //异步，thenCompose连接2个异步任务，下一个任务的输入是上一个任务的输出
                .map(future -> future.thenCompose(quote ->
                    CompletableFuture.supplyAsync(() -> DiscountService.applyDiscount(quote), executor)))
                //thenAccept在一个线程执行完成后执行处理，此处显示每个线程的完成结果+完成时间
                .map(future -> future.thenAccept(content ->
				    System.out.println(content + " (done in " + (System.currentTimeMillis() - start )+" ms)")))
                .toArray(size -> new CompletableFuture[size]);

        //所有线程执行完成后进行处理
        CompletableFuture.allOf(priceFuture).thenAccept((obj) -> System.out.println("all done"));
    }
    */

    public void findPrices(String product) {
        Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100));
        CompletableFuture<?>[] priceFuture = shops.stream()
                //异步，获取每个shop的名称、价格、折扣
                //thenCombine同时执行2个异步任务，并将2个异步任务的结果进行合并，线程池大小设置为shops.size() * 2
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor)
                        .thenCombine(
                                CompletableFuture.supplyAsync(() -> ExchangeService.getRate("USD", "CNY"), executor),
                                //quote是第1个异步任务的返回结果，rate是第2个异步任务的返回结果
                                (quote, rate) -> new Quote(quote.getShop(), quote.getPrice() * rate, quote.getDiscount())
                        )
                )
                //异步，thenCompose连接2个异步任务，下一个任务的输入是上一个任务的输出
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(() -> DiscountService.applyDiscount(quote), executor)))
                .toArray(size -> new CompletableFuture[size]);

        //所有线程执行完成后进行处理
        CompletableFuture.anyOf(priceFuture).thenAccept((obj) -> System.out.println("fastest done " + obj));
    }
}
