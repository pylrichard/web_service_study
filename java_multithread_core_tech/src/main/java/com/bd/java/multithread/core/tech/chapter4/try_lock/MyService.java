package com.bd.java.multithread.core.tech.chapter4.try_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private ReentrantLock lock = new ReentrantLock();

    public void tryLock() {
        String name = Thread.currentThread().getName();
        //锁未被另一个线程持有时才能获取
        if (lock.tryLock()) {
            System.out.println(name + " get lock");
        } else {
            System.out.println(name + " can't get lock");
        }
    }

    public void tryLockTimeOut() {
        String name = Thread.currentThread().getName();
        try {
            //锁在指定等待时间内没有被另一个线程持有，而且当前线程未被中断，可以获取锁
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(name + " get lock");
                Thread.sleep(2000);
            } else {
                System.out.println(name + " can't get lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
