package com.bd.java_multithread_core_tech.chapter2.sync_block_object;

public class ThreadA extends Thread {
    private Service service;

    public ThreadA(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        Object object = new Object();
        service.method(object);
    }
}
