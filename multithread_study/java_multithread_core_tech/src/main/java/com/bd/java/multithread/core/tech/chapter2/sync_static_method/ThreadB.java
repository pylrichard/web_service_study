package com.bd.java.multithread.core.tech.chapter2.sync_static_method;

public class ThreadB extends Thread {
    @Override
    public void run() {
        Service.methodB();
    }
}
