package com.bd.java.multithread.core.tech.chapter2.throw_exception_no_lock;

public class ThreadB extends Thread {
    public Service service;

    public ThreadB(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        service.method();
    }
}
