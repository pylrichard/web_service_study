package com.bd.java_multithread_core_tech.chapter3.wait_interrupt_exception;

public class MyThread extends Thread {
    private Object lock;

    public MyThread(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.method(lock);
    }
}
