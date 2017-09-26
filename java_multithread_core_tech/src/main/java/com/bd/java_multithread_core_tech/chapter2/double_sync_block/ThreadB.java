package com.bd.java_multithread_core_tech.chapter2.double_sync_block;

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
