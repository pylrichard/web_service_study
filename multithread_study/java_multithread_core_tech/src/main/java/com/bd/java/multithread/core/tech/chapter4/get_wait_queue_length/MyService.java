package com.bd.java.multithread.core.tech.chapter4.get_wait_queue_length;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void waitMethod() {
        try {
            lock.lock();
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void notifyMethod() {
        try {
            lock.lock();
            System.out.println(lock.getWaitQueueLength(condition) + " thread wait for condition");
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
