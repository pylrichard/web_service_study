package com.bd.java_multithread_core_tech.chapter1;

public class MyThread3Test {
    public static void main(String[] args) {
        MyThread3 t1 = new MyThread3(1);
        MyThread3 t2 = new MyThread3(2);
        MyThread3 t3 = new MyThread3(3);
        MyThread3 t4 = new MyThread3(4);
        MyThread3 t5 = new MyThread3(5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}