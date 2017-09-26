package com.bd.java_multithread_core_tech.chapter2.thread1;

public class Thread1Test {
    public static void main(String[] args) {
        HasSelfPrivateNum numRef = new HasSelfPrivateNum();
        ThreadA t1 = new ThreadA(numRef);
        t1.start();
        ThreadB t2 = new ThreadB(numRef);
        t2.start();
    }
}
