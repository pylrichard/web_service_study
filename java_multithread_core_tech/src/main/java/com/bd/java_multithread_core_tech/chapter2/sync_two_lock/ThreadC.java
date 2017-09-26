package com.bd.java_multithread_core_tech.chapter2.sync_two_lock;

public class ThreadC extends Thread {
    private Service service;

    public ThreadC(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.methodC();
    }
}
