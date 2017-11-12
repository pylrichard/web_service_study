package com.bd.java.multithread.core.tech.chapter2.sync_lock_in_inheritance;

import com.bd.java.multithread.core.tech.chapter2.sync_lock_in_inheritance.MyThread;

public class SyncLockInInheritanceTest {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}
