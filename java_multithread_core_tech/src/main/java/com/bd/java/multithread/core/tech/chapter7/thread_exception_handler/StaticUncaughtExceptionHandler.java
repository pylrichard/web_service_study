package com.bd.java.multithread.core.tech.chapter7.thread_exception_handler;

public class StaticUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("static exception handler");
        e.printStackTrace();
    }
}
