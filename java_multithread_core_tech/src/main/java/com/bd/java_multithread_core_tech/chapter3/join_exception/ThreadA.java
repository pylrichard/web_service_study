package com.bd.java_multithread_core_tech.chapter3.join_exception;

public class ThreadA extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Math.random();
        }
    }
}
