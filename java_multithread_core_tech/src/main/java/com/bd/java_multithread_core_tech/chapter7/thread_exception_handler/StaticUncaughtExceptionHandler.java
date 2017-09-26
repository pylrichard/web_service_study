package com.bd.java_multithread_core_tech.chapter7.thread_exception_handler;

public class StaticUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("static exception handler");
        e.printStackTrace();
    }
}
