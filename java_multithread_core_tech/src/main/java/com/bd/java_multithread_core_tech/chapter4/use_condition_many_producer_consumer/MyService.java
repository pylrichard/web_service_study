package com.bd.java_multithread_core_tech.chapter4.use_condition_many_producer_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();
    private boolean hasValue = false;

    public void set() {
        try {
            lock.lock();
            while (hasValue) {
                //多个线程在同一个Condition上等待
                //ThreadA唤醒的可能是同类线程获取到锁，即可能会多次打印set value
                System.out.println("many set value");
                condition.await();
            }
            System.out.println("set value");
            hasValue = true;
            //此处要唤醒全部等待线程
            //否则随机唤醒到同类线程，程序会假死
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void get() {
        try {
            lock.lock();
            while (!hasValue) {
                System.out.println("many get value");
                condition.await();
            }
            System.out.println("get value");
            hasValue = false;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
