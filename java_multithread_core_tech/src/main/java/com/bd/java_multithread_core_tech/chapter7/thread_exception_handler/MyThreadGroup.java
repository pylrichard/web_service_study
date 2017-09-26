package com.bd.java_multithread_core_tech.chapter7.thread_exception_handler;

public class MyThreadGroup extends ThreadGroup {
    public MyThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        super.uncaughtException(t, e);
        System.out.println("thread group exception handler");
    }
}
