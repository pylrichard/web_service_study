package com.bd.java_multithread_core_tech.chapter2.sync_lock_in_inheritance;

public class SyncLockInInheritanceTest {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}
