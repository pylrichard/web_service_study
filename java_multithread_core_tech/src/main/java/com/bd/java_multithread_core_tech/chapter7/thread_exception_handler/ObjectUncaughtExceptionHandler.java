package com.bd.java_multithread_core_tech.chapter7.thread_exception_handler;

public class ObjectUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("object exception handler");
        e.printStackTrace();
    }
}
