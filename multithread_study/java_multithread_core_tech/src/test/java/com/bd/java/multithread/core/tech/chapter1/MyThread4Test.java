package com.bd.java.multithread.core.tech.chapter1;

public class MyThread4Test {
    public static void main(String[] args) {
        MyThread4 t1 = new MyThread4("t1");
        MyThread4 t2 = new MyThread4("t2");
        MyThread4 t3 = new MyThread4("t3");

        t1.start();
        t2.start();
        t3.start();
    }
}
