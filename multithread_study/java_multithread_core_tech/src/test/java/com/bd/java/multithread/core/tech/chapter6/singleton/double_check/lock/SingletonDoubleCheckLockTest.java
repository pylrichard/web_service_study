package com.bd.java.multithread.core.tech.chapter6.singleton.double_check.lock;

public class SingletonDoubleCheckLockTest {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();
        t1.start();
        t2.start();
        t3.start();
    }
}
