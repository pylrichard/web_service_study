package com.bd.java_multithread_core_tech.chapter2.atomic_long_not_safe;

import java.util.concurrent.atomic.AtomicLong;

public class Service {
    public static AtomicLong count = new AtomicLong();

    public static AtomicLong getCount() {
        return count;
    }

    synchronized public void addCount() {
        System.out.println(Thread.currentThread().getName() + " add 100 : " + count.addAndGet(100));
        count.addAndGet(1);
    }
}
