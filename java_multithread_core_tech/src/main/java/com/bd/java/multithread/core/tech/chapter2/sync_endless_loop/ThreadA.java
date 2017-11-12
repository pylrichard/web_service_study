package com.bd.java.multithread.core.tech.chapter2.sync_endless_loop;

public class ThreadA extends Thread {
    private Service service;

    public ThreadA(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        //service.methodA();
        service.methodC();
    }
}
