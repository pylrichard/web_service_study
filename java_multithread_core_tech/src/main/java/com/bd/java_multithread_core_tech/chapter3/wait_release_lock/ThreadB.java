package com.bd.java_multithread_core_tech.chapter3.wait_release_lock;

public class ThreadB extends Thread {
    private Object lock;

    public ThreadB(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.method(lock);
    }
}
