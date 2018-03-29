package com.bd.java.multithread.core.tech.chapter1;

public class ThreadInterrupt6 extends Thread {
    private int i = 0;

    @Override
    public void run() {
        try {
            while(true) {
                i++;
                System.out.println("i = " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
