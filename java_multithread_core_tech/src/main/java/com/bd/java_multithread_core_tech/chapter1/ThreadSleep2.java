package com.bd.java_multithread_core_tech.chapter1;

public class ThreadSleep2 extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("run = " + this.currentThread().getName() + " begin = " + System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println("run = " + this.currentThread().getName() + " end = " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
