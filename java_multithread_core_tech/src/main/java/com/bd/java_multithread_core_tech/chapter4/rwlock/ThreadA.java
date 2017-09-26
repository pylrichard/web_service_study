package com.bd.java_multithread_core_tech.chapter4.rwlock;

public class ThreadA extends Thread {
    private MyService service;

    public ThreadA(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.read();
    }
}
