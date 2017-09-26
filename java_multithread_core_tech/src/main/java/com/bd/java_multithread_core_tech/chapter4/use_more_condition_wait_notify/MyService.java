package com.bd.java_multithread_core_tech.chapter4.use_more_condition_wait_notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private Lock lock = new ReentrantLock();
    public Condition conditionA = lock.newCondition();
    public Condition conditionB = lock.newCondition();

    public void awaitA() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            System.out.println(name + " awaitA() begin");
            conditionA.await();
            System.out.println(name + " awaitA() end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalA() {
        try {
            lock.lock();
            System.out.println("signalA()");
            conditionA.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void awaitB() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            System.out.println(name + " awaitB() begin");
            conditionB.await();
            System.out.println(name + " awaitB() end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalB() {
        try {
            lock.lock();
            System.out.println("signalB()");
            conditionB.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
