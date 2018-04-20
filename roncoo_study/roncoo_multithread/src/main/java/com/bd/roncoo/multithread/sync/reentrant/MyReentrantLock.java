package com.bd.roncoo.multithread.sync.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 可重入锁针对多个线程获取同一把锁和一个线程嵌套获取同一把锁
 */
public class MyReentrantLock implements Lock {
    private boolean isLocked = false;
    private Thread lockBy = null;
    private int lockCount = 0;

    /**
     * 注意添加synchronized关键字
     */
    @Override
    public synchronized void lock() {
        Thread currentThread = Thread.currentThread();
        while (isLocked && lockBy != currentThread) {
            try {
                System.out.println(currentThread.getName() + " wait");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(currentThread.getName() + " get lock");
        isLocked = true;
        lockBy = currentThread;
        lockCount++;
        System.out.println("lockCount:" + lockCount);
    }

    @Override
    public synchronized void unlock() {
        if (lockBy == Thread.currentThread()) {
            lockCount--;
            System.out.println("lockCount:" + lockCount);
            if (lockCount == 0) {
                notifyAll();
                isLocked = false;
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
