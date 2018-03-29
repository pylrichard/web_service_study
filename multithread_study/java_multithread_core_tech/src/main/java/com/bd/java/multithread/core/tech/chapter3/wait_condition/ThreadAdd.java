package com.bd.java.multithread.core.tech.chapter3.wait_condition;

public class ThreadAdd extends Thread {
    private AddService service;

    public ThreadAdd(AddService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.add();
    }
}
