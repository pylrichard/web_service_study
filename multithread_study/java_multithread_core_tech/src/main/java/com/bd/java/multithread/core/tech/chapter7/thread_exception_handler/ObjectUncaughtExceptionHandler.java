package com.bd.java.multithread.core.tech.chapter7.thread_exception_handler;

public class ObjectUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("object exception handler");
        e.printStackTrace();
    }
}
