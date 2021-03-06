package com.bd.java.multithread.core.tech.chapter3.wait_condition;

public class ThreadSub extends Thread {
    private SubService service;

    public ThreadSub(SubService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.sub();
    }
}
