package com.bd.java_multithread_core_tech.chapter4.use_condition_thread_run_in_order;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition对象对线程执行顺序进行排序
 */
public class MyService {
    volatile private static int runFlag = 1;
    private ReentrantLock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public void methodA() {
        try {
            lock.lock();
            while (runFlag != 1) {
                conditionA.await();
            }

            System.out.println(Thread.currentThread().getName());
            runFlag = 2;
            conditionB.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void methodB() {
        try {
            lock.lock();
            while (runFlag != 2) {
                conditionB.await();
            }

            System.out.println(Thread.currentThread().getName());
            runFlag = 3;
            conditionC.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void methodC() {
        try {
            lock.lock();
            while (runFlag != 3) {
                conditionC.await();
            }

            System.out.println(Thread.currentThread().getName());
            runFlag = 1;
            conditionA.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
