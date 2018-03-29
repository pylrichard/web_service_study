package com.bd.java.multithread.core.tech.chapter7.thread_exception_handler;

public class MyThread extends Thread {
    public MyThread(ThreadGroup group, String name) {
        super(group, name);
    }

    @Override
    public void run() {
        int num = Integer.parseInt("a");
    }
}
