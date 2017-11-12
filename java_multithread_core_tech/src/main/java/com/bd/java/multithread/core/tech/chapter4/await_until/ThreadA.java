package com.bd.java.multithread.core.tech.chapter4.await_until;

public class ThreadA extends Thread {
    private MyService service;

    public ThreadA(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.waitMethod();
    }
}
