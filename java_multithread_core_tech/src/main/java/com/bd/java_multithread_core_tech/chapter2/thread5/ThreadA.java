package com.bd.java_multithread_core_tech.chapter2.thread5;

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
