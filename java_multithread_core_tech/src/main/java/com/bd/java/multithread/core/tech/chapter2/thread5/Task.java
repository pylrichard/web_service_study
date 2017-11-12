package com.bd.java.multithread.core.tech.chapter2.thread5;

public class Task {
    public void doLongTimeTask() {
        //此处t1和t2会出现交叉打印
        for (int i = 0; i < 50; i++) {
            System.out.println("not synchronized thread name = " + Thread.currentThread().getName() + " i = " + (i + 1));
        }

        //此处不会出现t1和t2交叉打印，先获得对象锁的线程打印完毕后另一个线程才可以获得对象锁
        synchronized (this) {
            for (int i = 0; i < 20; i++) {
                System.out.println("synchronized thread name = " + Thread.currentThread().getName() + " i = " + (i + 1));
            }
        }
    }
}
