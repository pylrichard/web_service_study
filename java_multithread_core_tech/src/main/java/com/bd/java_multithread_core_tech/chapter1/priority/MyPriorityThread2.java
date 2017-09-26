package com.bd.java_multithread_core_tech.chapter1.priority;

public class MyPriorityThread2 extends Thread {
    @Override
    public void run() {
        System.out.println("MyThread2 priority " + this.getPriority());
    }
}
