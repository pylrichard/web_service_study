package com.bd.java.multithread.core.tech.chapter3.wait_release_lock;

public class ThreadA extends Thread {
    private Object lock;

    public ThreadA(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.method(lock);
    }
}
