package com.bd.java_multithread_core_tech.chapter4.reentrant_lock_more_method;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private Lock lock = new ReentrantLock();

    public void method1() {
        try {
            //调用lock.lock()的线程持有对象监视器，其它线程只有等待锁释放才能获取锁
            //与synchronized关键字一样
            lock.lock();
            String name = Thread.currentThread().getName();
            System.out.println("thread name = " + name);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void method2() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            System.out.println("thread name = " + name);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
