package com.bd.java_multithread_core_tech.chapter2.sync_not_extends;

public class Main {
    synchronized public void method() {
        try {
            System.out.println("thread name " + Thread.currentThread().getName() + " main method begin");
            Thread.sleep(100);
            System.out.println("thread name " + Thread.currentThread().getName() + " main method end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
