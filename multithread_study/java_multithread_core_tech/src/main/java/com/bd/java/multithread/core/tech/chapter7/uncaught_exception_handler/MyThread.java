package com.bd.java.multithread.core.tech.chapter7.uncaught_exception_handler;

public class MyThread extends Thread {
    @Override
    public void run() {
        String name = null;
        System.out.println(name.hashCode());
    }
}
