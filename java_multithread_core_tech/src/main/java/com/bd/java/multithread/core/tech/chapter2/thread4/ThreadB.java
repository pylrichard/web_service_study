package com.bd.java.multithread.core.tech.chapter2.thread4;

public class ThreadB extends Thread {
    private Task task;

    public ThreadB(Task task) {
        super();
        this.task = task;
    }

    @Override
    public void run() {
        CommonUtils.beginTime2 = System.currentTimeMillis();
        //task.doLongTimeTaskSync();
        task.doLongTimeTask();
        CommonUtils.endTime2 = System.currentTimeMillis();
    }
}
