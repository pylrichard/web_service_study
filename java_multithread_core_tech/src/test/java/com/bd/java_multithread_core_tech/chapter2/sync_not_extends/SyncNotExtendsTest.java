package com.bd.java_multithread_core_tech.chapter2.sync_not_extends;

public class SyncNotExtendsTest {
    public static void main(String[] args) {
        Sub sub = new Sub();
        ThreadA t1 = new ThreadA(sub);
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB(sub);
        t2.setName("t2");
        t2.start();
    }
}
