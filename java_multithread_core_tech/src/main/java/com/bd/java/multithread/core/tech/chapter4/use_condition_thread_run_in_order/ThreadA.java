package com.bd.java.multithread.core.tech.chapter4.use_condition_thread_run_in_order;

public class ThreadA extends Thread {
    private MyService service;

    public ThreadA(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.methodA();
    }
}
