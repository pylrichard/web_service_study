package com.bd.java_multithread_core_tech.chapter2.sync_static_method;

public class ThreadB extends Thread {
    @Override
    public void run() {
        Service.methodB();
    }
}
