package com.bd.java_multithread_core_tech.chapter4.reentrant_lock_more_method;

public class ThreadB extends Thread {
    private MyService service;

    public ThreadB(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.method2();
    }
}
