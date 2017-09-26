package com.bd.java_multithread_core_tech.chapter2.sync_block_lock_all.test1;

public class ThreadB extends Thread {
    private Service service;
    private MyObject object;

    public ThreadB(Service service, MyObject object) {
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
