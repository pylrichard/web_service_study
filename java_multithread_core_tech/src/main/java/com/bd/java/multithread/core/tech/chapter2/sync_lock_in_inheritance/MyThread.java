package com.bd.java.multithread.core.tech.chapter2.sync_lock_in_inheritance;

public class MyThread extends Thread {
    @Override
    public void run() {
        Sub sub = new Sub();
        sub.subMethod();
    }
}
