package com.bd.java.multithread.core.tech.chapter1.priority;

import com.bd.java.multithread.core.tech.chapter1.priority.MyPriorityThread1;

public class PriorityTest {
    public static void main(String[] args) {
        System.out.println("priority " + Thread.currentThread().getPriority());
        //Thread.currentThread().setPriority(6);
        //线程优先级具有继承性
        System.out.println("priority " + Thread.currentThread().getPriority());

        MyPriorityThread1 t = new MyPriorityThread1();
        t.start();
    }
}
