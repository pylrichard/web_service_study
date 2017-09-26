package com.bd.java_multithread_core_tech.chapter4.rwlock;

public class RWLockTest {
    public static void main(String[] args) {
        MyService service = new MyService();
        ThreadB t2 = new ThreadB(service);
        t2.setName("t2");
        t2.start();
        ThreadA t1 = new ThreadA(service);
        t1.setName("t1");
        t1.start();
    }
}
