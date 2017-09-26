package com.bd.java_multithread_core_tech.chapter1.priority;

public class MyPriorityThread1 extends Thread {
    @Override
    public void run() {
        System.out.println("MyThread1 priority " + this.getPriority());
        MyPriorityThread2 t2 = new MyPriorityThread2();
        t2.start();
    }
}
