package com.bd.java_multithread_core_tech.chapter4.rwlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyService {
    volatile private String value = "Hello Java";
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    //读锁之间是异步的，不互斥
    public void read() {
        try {
            String name = Thread.currentThread().getName();
            rwLock.readLock().lock();
            System.out.println(name + " get read lock value = " + value);
            Thread.sleep(2000);
            System.out.println(name + " read end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }
    }

    //读锁和写锁是互斥的
    //如果t1先获取读锁，此时t2无法获取写锁
    //如果t2先获取写锁，此时t1无法获取读锁
    public void write() {
        try {
            String name = Thread.currentThread().getName();
            rwLock.writeLock().lock();
            System.out.println(name + " get value " + value);
            value = "Hello Scala";
            System.out.println(name + " get write lock value = " + value);
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
