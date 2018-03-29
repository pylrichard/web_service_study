package com.bd.java.multithread.core.tech.chapter4.use_condition_many_producer_consumer;

public class ThreadA extends Thread {
    private MyService service;

    public ThreadA(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            service.set();
        }
    }
}
