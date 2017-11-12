package com.bd.java.multithread.core.tech.chapter3.wait_notify_demo;

import com.bd.java.multithread.core.tech.chapter3.wait_notify_demo.ThreadA;
import com.bd.java.multithread.core.tech.chapter3.wait_notify_demo.ThreadB;

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
