package com.bd.java.multithread.core.tech.chapter4.reentrant_lock_more_method;

public class ThreadA extends Thread {
    private MyService service;

    public ThreadA(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.method1();
    }
}
