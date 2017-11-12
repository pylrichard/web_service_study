package com.bd.java.multithread.core.tech.chapter3.inheritable_thread_local;

public class ThreadA extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("ta get value = " + Tools.getTL());
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
