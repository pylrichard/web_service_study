package com.bd.java.multithread.core.tech.chapter4.get_hold_count;

import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private ReentrantLock lock = new ReentrantLock();

    public void method1() {
        try {
            lock.lock();
            //查询当前线程保持锁的次数，即调用lock()的次数
            System.out.println("getHoldCount() = " + lock.getHoldCount());
            method2();
        } finally {
            lock.unlock();
        }
    }

    public void method2() {
        try {
            lock.lock();
            System.out.println("getHoldCount() = " + lock.getHoldCount());
        } finally {
            lock.unlock();
        }
    }
}
