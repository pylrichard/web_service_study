package com.bd.java_multithread_core_tech.chapter4.reentrant_lock_fair_nofair;

import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private ReentrantLock lock;

    public MyService(boolean isFair) {
        super();
        lock = new ReentrantLock(isFair);
    }

    public void method() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " get lock"
                                //当前线程是否持有锁
                                + " by current thread is " + lock.isHeldByCurrentThread()
                                //锁是否被线程持有
                                + " lock get by any thread is " + lock.isLocked()
                                //是否是公平锁
                                + " fair lock is " + lock.isFair());
        } finally {
            lock.unlock();
        }
    }
}
