package com.bd.java_multithread_core_tech.chapter2.thread4;

public class ThreadA extends Thread {
    private Task task;

    public ThreadA(Task task) {
        super();
        this.task = task;
    }

    @Override
    public void run() {
        CommonUtils.beginTime1 = System.currentTimeMillis();
        //task.doLongTimeTaskSync();
        task.doLongTimeTask();
        CommonUtils.endTime1 = System.currentTimeMillis();
    }
}
