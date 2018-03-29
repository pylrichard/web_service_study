package com.bd.java.multithread.core.tech.chapter4.has_queued_thread;

import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private ReentrantLock lock = new ReentrantLock();

    public ReentrantLock getLock() {
        return lock;
    }

    public void waitMethod() {
        try {
            lock.lock();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
