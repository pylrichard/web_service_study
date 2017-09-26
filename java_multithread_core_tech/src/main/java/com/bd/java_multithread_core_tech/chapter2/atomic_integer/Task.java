package com.bd.java_multithread_core_tech.chapter2.atomic_integer;

import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Runnable {
    private AtomicInteger count = new AtomicInteger(0);

    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(count.incrementAndGet());
        }
    }
}
