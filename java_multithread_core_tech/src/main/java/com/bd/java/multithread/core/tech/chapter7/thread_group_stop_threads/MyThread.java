package com.bd.java.multithread.core.tech.chapter7.thread_group_stop_threads;

public class MyThread extends Thread {
    public MyThread(ThreadGroup group, String name) {
        super(group, name);
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {}
        System.out.println("thread end");
    }
}
