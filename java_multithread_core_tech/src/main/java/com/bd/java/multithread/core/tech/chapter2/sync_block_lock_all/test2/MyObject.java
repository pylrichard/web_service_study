package com.bd.java.multithread.core.tech.chapter2.sync_block_lock_all.test2;

public class MyObject {
    public synchronized void method1() {
        System.out.println("object method1 get lock thread name = " + Thread.currentThread().getName());
        System.out.println("object method1 release lock thread name = " + Thread.currentThread().getName());
    }

    public void method2() {
        synchronized (this) {
            System.out.println("object method2 get lock thread name = " + Thread.currentThread().getName());
            System.out.println("object method2 release lock thread name = " + Thread.currentThread().getName());
        }
    }
}
