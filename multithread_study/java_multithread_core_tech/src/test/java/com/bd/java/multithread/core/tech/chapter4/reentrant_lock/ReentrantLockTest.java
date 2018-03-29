package com.bd.java.multithread.core.tech.chapter4.reentrant_lock;

public class ReentrantLockTest {
    public static void main(String[] args) {
        MyService service = new MyService();
        MyThread t1 = new MyThread(service);
        MyThread t2 = new MyThread(service);
        MyThread t3 = new MyThread(service);
        t1.start();
        t2.start();
        t3.start();
    }
}
