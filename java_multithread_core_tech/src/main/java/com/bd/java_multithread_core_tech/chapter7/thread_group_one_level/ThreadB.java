package com.bd.java_multithread_core_tech.chapter7.thread_group_one_level;

public class ThreadB extends Thread {
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("thread " + Thread.currentThread().getName());
                Thread.sleep(1500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
