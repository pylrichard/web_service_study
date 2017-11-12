package com.bd.java.multithread.core.tech.chapter2.sync_two_lock;

public class Service {
    //synchronized添加在static方法上，是对Service类进行加锁
    synchronized public static void methodA() {
        try {
            System.out.println("methodA begin thread name = " + Thread.currentThread().getName());
            Thread.sleep(1000);
            System.out.println("methodA end thread name = " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public static void methodB() {
        System.out.println("methodB begin thread name = " + Thread.currentThread().getName());
        System.out.println("methodB end thread name = " + Thread.currentThread().getName());
    }

    //synchronized添加在非static方法上，是对Service对象进行加锁
    synchronized public void methodC() {
        System.out.println("methodC begin thread name = " + Thread.currentThread().getName());
        System.out.println("methodC end thread name = " + Thread.currentThread().getName());
    }
}
