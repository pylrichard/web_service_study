package com.bd.java.multithread.core.tech.chapter2.sync_two_object_static_method;

public class ThreadB extends Thread {
    private Service service;

    public ThreadB(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.methodB();
    }
}
