package com.bd.java_multithread_core_tech.chapter3.wait_notify_demo;

public class WaitNotifyDemoTest {
    public static void main(String[] args) {
        try {
            Object lock = new Object();
            ThreadA t1 = new ThreadA(lock);
            t1.start();
            Thread.sleep(3000);
            ThreadB t2 = new ThreadB(lock);
            t2.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
