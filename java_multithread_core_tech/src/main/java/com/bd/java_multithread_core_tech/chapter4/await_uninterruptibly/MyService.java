package com.bd.java_multithread_core_tech.chapter4.await_uninterruptibly;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void method() {
        try {
            lock.lock();
            System.out.println("await begin");
            //线程被中断，触发异常
//            condition.await();
            //不会触发异常
            condition.awaitUninterruptibly();
            System.out.println("await end");
        } finally {
            lock.unlock();
        }
    }
}
