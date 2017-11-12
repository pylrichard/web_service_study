package com.bd.java.multithread.core.tech.chapter2.sync_lock_properties_changed;

public class ThreadB extends Thread {
    private Service service;
    private UserInfo info;

    public ThreadB(Service service, UserInfo info) {
        super();
        this.service = service;
        this.info = info;
    }

    @Override
    public void run() {
        service.method(info);
    }
}
