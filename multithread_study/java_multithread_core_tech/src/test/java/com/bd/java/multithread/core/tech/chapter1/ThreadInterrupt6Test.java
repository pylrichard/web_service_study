package com.bd.java.multithread.core.tech.chapter1;

public class ThreadInterrupt6Test {
    public static void main(String[] args) {
        try {
            ThreadInterrupt6 t = new ThreadInterrupt6();
            t.start();
            Thread.sleep(10000);
            t.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
