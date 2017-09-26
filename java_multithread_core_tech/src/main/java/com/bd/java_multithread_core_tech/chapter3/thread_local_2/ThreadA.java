package com.bd.java_multithread_core_tech.chapter3.thread_local_2;

public class ThreadA extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                Tools.setTL("ta " + (i + 1));
                System.out.println("ta get value = " + Tools.getTL());
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
