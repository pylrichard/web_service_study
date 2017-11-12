package com.bd.java.multithread.core.tech.chapter1;

public class ThreadSleep1 extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("run = " + this.currentThread().getName() + " begin");
            Thread.sleep(1000);
            System.out.println("run = " + this.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
