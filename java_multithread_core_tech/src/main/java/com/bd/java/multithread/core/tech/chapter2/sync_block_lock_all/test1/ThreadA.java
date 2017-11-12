package com.bd.java.multithread.core.tech.chapter2.sync_block_lock_all.test1;

public class ThreadA extends Thread {
    private Service service;
    private MyObject object;

    public ThreadA(Service service, MyObject object) {
        super();
        this.service = service;
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        service.method(object);
    }
}
