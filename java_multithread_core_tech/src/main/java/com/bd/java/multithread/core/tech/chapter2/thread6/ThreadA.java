package com.bd.java.multithread.core.tech.chapter2.thread6;

public class ThreadA extends Thread {
    private Task task;

    public ThreadA(Task task) {
        super();
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        task.doLongTimeTask();
    }
}
