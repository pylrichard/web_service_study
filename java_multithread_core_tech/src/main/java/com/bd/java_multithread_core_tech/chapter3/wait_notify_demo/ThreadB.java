package com.bd.java_multithread_core_tech.chapter3.wait_notify_demo;

public class ThreadB extends Thread {
    private Object lock;

    public ThreadB(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            System.out.println("notify begin");
            lock.notify();
            System.out.println("notify end");
        }
    }
}
