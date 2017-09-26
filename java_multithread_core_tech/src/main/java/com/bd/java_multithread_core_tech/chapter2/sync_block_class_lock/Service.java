package com.bd.java_multithread_core_tech.chapter2.sync_block_class_lock;

public class Service {
    public static void methodA() {
        synchronized (Service.class) {
            try {
                System.out.println("methodA begin thread name = " + Thread.currentThread().getName());
                Thread.sleep(1500);
                System.out.println("methodA end thread name = " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void methodB() {
        synchronized (Service.class) {
            System.out.println("methodB begin thread name = " + Thread.currentThread().getName());
            System.out.println("methodB end thread name = " + Thread.currentThread().getName());
        }
    }
}
