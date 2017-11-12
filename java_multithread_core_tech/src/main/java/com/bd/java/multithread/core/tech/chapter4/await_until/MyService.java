package com.bd.java.multithread.core.tech.chapter4.await_until;

import java.util.Calendar;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void waitMethod() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 5);
            lock.lock();
            System.out.println("awaitUntil begin");
            condition.awaitUntil(calendar.getTime());
            System.out.println("awaitUntil end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void notifyMethod() {
        try {
            lock.lock();
            condition.signalAll();
            System.out.println("signalAll");
        } finally {
            lock.unlock();
        }
    }
}
