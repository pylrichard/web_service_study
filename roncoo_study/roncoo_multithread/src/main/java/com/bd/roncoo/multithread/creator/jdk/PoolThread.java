package com.bd.roncoo.multithread.creator.jdk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池创建方式
 */
public class PoolThread {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        threadPool.shutdown();
    }
}
