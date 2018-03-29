package com.bd.java.multithread.core.tech.chapter3.wait_notify_two_thread_trans_data;

public class WaitNotifyTwoThreadTransDataTest {
    public static void main(String[] args) {
        try {
            Object lock = new Object();
            ThreadA t1 = new ThreadA(lock);
            t1.setName("t1");
            t1.start();
            Thread.sleep(100);
            ThreadB t2 = new ThreadB(lock);
            t2.setName("t2");
            t2.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
