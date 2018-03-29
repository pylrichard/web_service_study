package com.bd.java.multithread.core.tech.chapter3.thread_local_3;

public class ThreadLocal3Test {
    public static void main(String[] args) {
        try {
            ThreadA ta = new ThreadA();
            ta.start();
            Thread.sleep(1000);
            Thread tb = new ThreadB();
            tb.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
