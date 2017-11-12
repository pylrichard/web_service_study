package com.bd.java.multithread.core.tech.chapter2.sync_block_object;

public class ThreadB extends Thread {
    private Service service;

    public ThreadB(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        Object object = new Object();
        service.method(object);
    }
}
