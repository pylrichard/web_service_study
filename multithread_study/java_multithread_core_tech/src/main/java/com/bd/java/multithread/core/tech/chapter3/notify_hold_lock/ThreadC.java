package com.bd.java.multithread.core.tech.chapter3.notify_hold_lock;

public class ThreadC extends Thread {
    private Object lock;

    public ThreadC(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.notifyMethod(lock);
    }
}
