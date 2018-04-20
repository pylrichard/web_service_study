package com.bd.roncoo.multithread.sync.reentrant;

import java.util.concurrent.locks.Lock;

public class MyReentrantLockTask {
    Lock lock = new MyReentrantLock();

    public static void main(String[] args) {
        MyReentrantLockTask task = new MyReentrantLockTask();
        new Thread(task::method1).start();
    }

    public void method1() {
        lock.lock();
        System.out.println("method1");
        method2();
        lock.unlock();
    }

    public void method2() {
        lock.lock();
        System.out.println("method2");
        method3();
        lock.unlock();
    }

    public void method3() {
        lock.lock();
        System.out.println("method3");
        lock.unlock();
    }
}
