package com.bd.java.multithread.core.tech.chapter1;

public class ThreadInterrupt4Test {
    public static void main(String[] args) {
        try {
            ThreadInterrupt4 t = new ThreadInterrupt4();
            t.start();
            Thread.sleep(10);
            t.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}