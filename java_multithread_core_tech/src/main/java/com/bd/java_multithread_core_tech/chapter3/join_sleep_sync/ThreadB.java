package com.bd.java_multithread_core_tech.chapter3.join_sleep_sync;

public class ThreadB extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("tb run begin " + System.currentTimeMillis());
            Thread.sleep(4000);
            System.out.println("tb run end " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void method() {
        System.out.println("tb sync method " + System.currentTimeMillis());
    }
}
