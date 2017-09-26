package com.bd.java_multithread_core_tech.chapter1;

public class ThreadInterrupt8Test {
    public static void main(String[] args) throws InterruptedException {
        ThreadInterrupt8 t = new ThreadInterrupt8();
        t.start();
        Thread.sleep(1000);
        t.interrupt();
    }
}
