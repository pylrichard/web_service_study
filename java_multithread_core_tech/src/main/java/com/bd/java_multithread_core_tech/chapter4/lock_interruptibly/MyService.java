package com.bd.java_multithread_core_tech.chapter4.lock_interruptibly;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void waitMethod() {
        try {
            String name = Thread.currentThread().getName();
            System.out.println(name + " lock begin");
            //t2线程被打断，lock()不会触发异常，正常执行
//            lock.lock();
            //lockInterruptibly()表示当前线程(t2)未被中断，则可以获取锁
            //已经被中断则触发异常
            lock.lockInterruptibly();
            //模拟线程运行逻辑
            for (int i = 0; i < Integer.MAX_VALUE / 10; i++) {
                Math.random();
            }
            System.out.println(name + " lock end");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " isAlive " + Thread.currentThread().isAlive());
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
