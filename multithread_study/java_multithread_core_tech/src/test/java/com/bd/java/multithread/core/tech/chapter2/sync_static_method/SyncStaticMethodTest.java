package com.bd.java.multithread.core.tech.chapter2.sync_static_method;

public class SyncStaticMethodTest {
    public static void main(String[] args) {
        ThreadA t1 = new ThreadA();
        t1.setName("t1");
        t1.start();

        ThreadB t2 = new ThreadB();
        t2.setName("t2");
        t2.start();
    }
}
