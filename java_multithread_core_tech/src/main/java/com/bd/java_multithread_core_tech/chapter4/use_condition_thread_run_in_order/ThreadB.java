package com.bd.java_multithread_core_tech.chapter4.use_condition_thread_run_in_order;

public class ThreadB extends Thread {
    private MyService service;

    public ThreadB(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.methodB();
    }
}
