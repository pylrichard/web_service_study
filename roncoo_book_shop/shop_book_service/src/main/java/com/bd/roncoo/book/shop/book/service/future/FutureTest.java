package com.bd.roncoo.book.shop.book.service.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FutureTest {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();
        //执行submit()后，main线程立即返回执行下一条语句
        Future<String> future = executorService.submit(FutureTest::doTask);
        long end1 = System.currentTimeMillis();
        System.out.println("time: " + (end1 - start));
        //此处发生等待，2s没有返回则超时
        String result = future.get(2, TimeUnit.SECONDS);
        long end2 = System.currentTimeMillis();
        System.out.println("time: " + (end2 - end1) + " result: " + result);
    }

    public static String doTask() throws InterruptedException {
        Thread.sleep(1000);

        return "pyl";
    }
}
