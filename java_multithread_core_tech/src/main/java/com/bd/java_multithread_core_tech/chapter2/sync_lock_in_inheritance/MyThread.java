package com.bd.java_multithread_core_tech.chapter2.sync_lock_in_inheritance;

public class MyThread extends Thread {
    @Override
    public void run() {
        Sub sub = new Sub();
        sub.subMethod();
    }
}
