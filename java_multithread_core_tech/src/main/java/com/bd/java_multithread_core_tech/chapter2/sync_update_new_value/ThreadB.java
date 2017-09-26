package com.bd.java_multithread_core_tech.chapter2.sync_update_new_value;

public class ThreadB extends Thread {
    private Service service;

    public ThreadB(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.stop();
    }
}
