package com.bd.java_multithread_core_tech.chapter4.reentrant_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private Lock lock = new ReentrantLock();

    public void method() {
        lock.lock();
        System.out.println("thread name = " + Thread.currentThread().getName());
        lock.unlock();
    }
}
