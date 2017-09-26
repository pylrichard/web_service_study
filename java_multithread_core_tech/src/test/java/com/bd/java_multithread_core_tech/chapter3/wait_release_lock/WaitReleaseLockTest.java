package com.bd.java_multithread_core_tech.chapter3.wait_release_lock;

public class WaitReleaseLockTest {
    public static void main(String[] args) {
        Object lock = new Object();
        ThreadA t1 = new ThreadA(lock);
        t1.start();
        ThreadB t2 = new ThreadB(lock);
        t2.start();
    }
}
