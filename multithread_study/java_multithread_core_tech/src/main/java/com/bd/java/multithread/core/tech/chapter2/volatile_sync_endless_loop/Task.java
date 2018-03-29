package com.bd.java.multithread.core.tech.chapter2.volatile_sync_endless_loop;

public class Task implements Runnable {
    private boolean isRunning = true;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void method() {
        /*
        while (isRunning == true) {
            System.out.println("thread name = " + Thread.currentThread().getName());
        }*/

        try {
            while (isRunning == true) {
                System.out.println("thread name = " + Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        method();
    }
}
