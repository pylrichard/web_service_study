package com.bd.java.multithread.core.tech.chapter2.sync_method_lock_object;

public class SyncMethodLockObjectTest {
    public static void main(String[] args) {
        MyObject obj = new MyObject();
        ThreadA t1 = new ThreadA(obj);
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB(obj);
        t2.setName("t2");
        t2.start();
    }
}
