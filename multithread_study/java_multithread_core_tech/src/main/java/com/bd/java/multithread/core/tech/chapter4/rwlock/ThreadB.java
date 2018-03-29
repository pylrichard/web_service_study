package com.bd.java.multithread.core.tech.chapter4.rwlock;

public class ThreadB extends Thread {
    private MyService service;

    public ThreadB(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.write();
    }
}
