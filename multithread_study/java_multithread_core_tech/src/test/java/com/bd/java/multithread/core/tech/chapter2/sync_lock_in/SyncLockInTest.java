package com.bd.java.multithread.core.tech.chapter2.sync_lock_in;

public class SyncLockInTest {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}
