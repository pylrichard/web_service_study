package com.bd.java_multithread_core_tech.chapter7.thread_exception_handler;

public class ThreadExceptionHandlerTest {
    public static void main(String[] args) {
        MyThreadGroup group = new MyThreadGroup("group");
        MyThread t = new MyThread(group, "t");
        t.setUncaughtExceptionHandler(new ObjectUncaughtExceptionHandler());
        MyThread.setDefaultUncaughtExceptionHandler(new StaticUncaughtExceptionHandler());
        t.start();
    }
}
