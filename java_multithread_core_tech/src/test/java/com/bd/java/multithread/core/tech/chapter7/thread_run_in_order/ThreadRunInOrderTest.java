package com.bd.java.multithread.core.tech.chapter7.thread_run_in_order;

import com.bd.java.multithread.core.tech.chapter7.thread_run_in_order.MyThread;

public class ThreadRunInOrderTest {
    public static void main(String[] args) {
        Object lock = new Object();
        MyThread t1 = new MyThread(lock, 1);
        MyThread t2 = new MyThread(lock, 2);
        //最后一个运行的线程执行顺序索引为0
        MyThread t3 = new MyThread(lock, 0);
        t1.setName("t1");
        t2.setName("t2");
        t3.setName("t3");
        t1.start();
        t2.start();
        t3.start();
    }
}
