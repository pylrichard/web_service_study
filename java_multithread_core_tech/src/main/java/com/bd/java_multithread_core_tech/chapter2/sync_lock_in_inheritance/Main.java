package com.bd.java_multithread_core_tech.chapter2.sync_lock_in_inheritance;

public class Main {
    public int i = 10;

    synchronized public void mainMethod() {
        try {
            i--;
            System.out.println("mainMethod i = " + i);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
