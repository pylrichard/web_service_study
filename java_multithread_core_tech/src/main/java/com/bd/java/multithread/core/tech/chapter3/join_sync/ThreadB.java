package com.bd.java.multithread.core.tech.chapter3.join_sync;

public class ThreadB extends Thread {
    @Override
    synchronized public void run() {
        try {
            System.out.println("tb run begin");
            Thread.sleep(5000);
            System.out.println("tb run end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
