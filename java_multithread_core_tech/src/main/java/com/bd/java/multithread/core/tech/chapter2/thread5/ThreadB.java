package com.bd.java.multithread.core.tech.chapter2.thread5;

public class ThreadB extends Thread {
    private Task task;

    public ThreadB(Task task) {
        super();
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        task.doLongTimeTask();
    }
}
