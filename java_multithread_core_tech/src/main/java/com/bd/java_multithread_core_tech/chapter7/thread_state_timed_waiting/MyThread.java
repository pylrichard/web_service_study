package com.bd.java_multithread_core_tech.chapter7.thread_state_timed_waiting;

public class MyThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
