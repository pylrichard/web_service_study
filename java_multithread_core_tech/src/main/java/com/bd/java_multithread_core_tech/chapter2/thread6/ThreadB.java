package com.bd.java_multithread_core_tech.chapter2.thread6;

public class ThreadB extends Thread {
    private Task task;

    public ThreadB(Task task) {
        super();
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        task.method();
    }
}
