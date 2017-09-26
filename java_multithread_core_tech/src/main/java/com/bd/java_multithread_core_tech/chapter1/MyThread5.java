package com.bd.java_multithread_core_tech.chapter1;

public class MyThread5 extends Thread {
    private int count = 5;

    synchronized public void run() {
        super.run();

        //不要使用for语句，使用同步后其他线程得不到运行机会，由一个线程进行减法运算
        count--;
        System.out.println("run = " + this.currentThread().getName() + " count: " + count);
    }
}
