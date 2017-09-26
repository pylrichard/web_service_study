package com.bd.java_multithread_core_tech.chapter3.join_sleep;

public class MyThread extends Thread {
    @Override
    public void run() {
        try {
            int value = (int)(Math.random() * 1000);
            System.out.println(value);
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
