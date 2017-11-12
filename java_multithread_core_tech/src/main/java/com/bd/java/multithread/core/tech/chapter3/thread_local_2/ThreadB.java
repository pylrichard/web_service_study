package com.bd.java.multithread.core.tech.chapter3.thread_local_2;

public class ThreadB extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                Tools.setTL("tb " + (i + 1));
                System.out.println("tb get value = " + Tools.getTL());
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
