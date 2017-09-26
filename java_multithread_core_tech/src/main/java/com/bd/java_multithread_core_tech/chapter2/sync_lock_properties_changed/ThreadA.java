package com.bd.java_multithread_core_tech.chapter2.sync_lock_properties_changed;

public class ThreadA extends Thread {
    private Service service;
    private UserInfo info;

    public ThreadA(Service service, UserInfo info) {
        super();
        this.service = service;
        this.info = info;
    }

    @Override
    public void run() {
        service.method(info);
    }
}
