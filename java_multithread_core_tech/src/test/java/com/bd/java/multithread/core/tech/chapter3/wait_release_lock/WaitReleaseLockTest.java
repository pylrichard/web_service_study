package com.bd.java.multithread.core.tech.chapter3.wait_release_lock;

import com.bd.java.multithread.core.tech.chapter3.wait_release_lock.ThreadA;
import com.bd.java.multithread.core.tech.chapter3.wait_release_lock.ThreadB;

public class WaitReleaseLockTest {
    public static void main(String[] args) {
        Object lock = new Object();
        ThreadA t1 = new ThreadA(lock);
        t1.start();
        ThreadB t2 = new ThreadB(lock);
        t2.start();
    }
}
