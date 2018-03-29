package com.bd.java.multithread.core.tech.chapter3.wait_notify_demo;

public class ThreadA extends Thread {
    private Object lock;

    public ThreadA(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                System.out.println("wait begin");
                lock.wait();
                System.out.println("wait end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
