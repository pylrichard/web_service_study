package com.bd.java.multithread.core.tech.chapter3.notify_one_all;

public class ThreadD extends Thread {
    private Object lock;

    public ThreadD(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            //notify()会随机唤醒一个处于wait状态的线程
            //lock.notify();

            //notifyAll()会唤醒所有处于wait状态的线程
            lock.notifyAll();
        }
    }
}
