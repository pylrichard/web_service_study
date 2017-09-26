package com.bd.java_multithread_core_tech.chapter1;

public class ThreadInterrupt2Test {
    public static void main(String[] args) {
        try {
            ThreadInterrupt2 t = new ThreadInterrupt2();
            t.start();
            Thread.sleep(10);
            t.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
