package com.bd.java.multithread.core.tech.chapter1;

public class MyThread3 extends Thread {
    private int i;

    public MyThread3(int i) {
        //super();
        this.i = i;
    }

    public void run() {
        System.out.println("i = " + i);
    }
}
