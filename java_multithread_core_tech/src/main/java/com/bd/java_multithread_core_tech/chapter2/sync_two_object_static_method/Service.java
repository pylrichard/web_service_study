package com.bd.java_multithread_core_tech.chapter2.sync_two_object_static_method;

public class Service {
    synchronized public static void methodA() {
        try {
            System.out.println("methodA begin thread name = " + Thread.currentThread().getName());
            Thread.sleep(3000);
            System.out.println("methodA end thread name = " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public static void methodB() {
        System.out.println("methodB begin thread name = " + Thread.currentThread().getName());
        System.out.println("methodB end thread name = " + Thread.currentThread().getName());
    }
}
