package com.bd.java_multithread_core_tech.chapter1;

public class MyThread6 extends Thread {
    public MyThread6() {
        System.out.println("constructor is called by " + Thread.currentThread().getName());
    }

    public void run() {
        System.out.println("run is called by " + Thread.currentThread().getName());
    }
}
