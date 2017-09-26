package com.bd.java_multithread_core_tech.chapter2.atomic_long_not_safe;

public class MyThread extends Thread {
    private Service service;

    public MyThread(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.addCount();
    }
}
