package com.bd.java.multithread.core.tech.chapter4.await_uninterruptibly;

public class MyThread extends Thread {
    private MyService service;

    public MyThread(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.method();
    }
}
