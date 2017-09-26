package com.bd.java_multithread_core_tech.chapter4.use_condition_wait_notify;

public class MyThread extends Thread {
    private MyService service;

    public MyThread(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.await();
    }
}
