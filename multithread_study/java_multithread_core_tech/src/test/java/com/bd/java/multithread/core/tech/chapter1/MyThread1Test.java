package com.bd.java.multithread.core.tech.chapter1;

public class MyThread1Test {
    public static void main(String[] args) {
        MyThread1 t = new MyThread1();
        t.start();
        //t.start();
        System.out.println("MyThreadTest");
    }
}