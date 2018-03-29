package com.bd.java.multithread.core.tech.chapter1;

public class MyRunnableTest {
    public static void main(String[] args) {
        Runnable r = new MyRunnable();
        Thread t = new Thread(r);
        t.start();
        System.out.println("MyRunnableTest");
    }
}
