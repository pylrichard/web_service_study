package com.bd.java.multithread.core.tech.chapter3.notify_one_all;

public class NotifyOneAllTest {
    public static void main(String[] args) {
        try {
            Object lock = new Object();
            ThreadA t1 = new ThreadA(lock);
            t1.setName("t1");
            t1.start();
            ThreadB t2 = new ThreadB(lock);
            t2.setName("t2");
            t2.start();
            ThreadC t3 = new ThreadC(lock);
            t3.setName("t3");
            t3.start();
            Thread.sleep(1000);
            ThreadD t4 = new ThreadD(lock);
            t4.setName("t4");
            t4.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
