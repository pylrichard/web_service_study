package com.bd.java.multithread.core.tech.chapter3.notify_hold_lock;

import com.bd.java.multithread.core.tech.chapter3.notify_hold_lock.ThreadA;
import com.bd.java.multithread.core.tech.chapter3.notify_hold_lock.ThreadB;
import com.bd.java.multithread.core.tech.chapter3.notify_hold_lock.ThreadC;

public class NotifyHoldLockTest {
    public static void main(String[] args) throws InterruptedException {
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
    }
}
