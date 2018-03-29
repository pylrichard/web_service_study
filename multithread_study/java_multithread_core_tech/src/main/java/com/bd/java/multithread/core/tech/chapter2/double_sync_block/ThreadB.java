package com.bd.java.multithread.core.tech.chapter2.double_sync_block;

public class ThreadB extends Thread {
    private Service service;

    public ThreadB(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        service.methodB();
    }
}
