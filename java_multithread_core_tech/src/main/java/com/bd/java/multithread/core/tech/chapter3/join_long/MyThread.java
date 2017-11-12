package com.bd.java.multithread.core.tech.chapter3.join_long;

public class MyThread extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("begin time " + System.currentTimeMillis());
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
