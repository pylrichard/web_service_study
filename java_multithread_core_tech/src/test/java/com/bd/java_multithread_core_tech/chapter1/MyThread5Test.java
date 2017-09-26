package com.bd.java_multithread_core_tech.chapter1;

public class MyThread5Test {
    public static void main(String[] args) {
        MyThread5 t = new MyThread5();
        Thread t1 = new Thread(t, "t1");
        Thread t2 = new Thread(t, "t2");
        Thread t3 = new Thread(t, "t3");
        Thread t4 = new Thread(t, "t4");
        Thread t5 = new Thread(t, "t5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
