package com.bd.java.multithread.core.tech.chapter7.thread_exception_handler;

import com.bd.java.multithread.core.tech.chapter7.thread_exception_handler.MyThread;
import com.bd.java.multithread.core.tech.chapter7.thread_exception_handler.MyThreadGroup;
import com.bd.java.multithread.core.tech.chapter7.thread_exception_handler.ObjectUncaughtExceptionHandler;
import com.bd.java.multithread.core.tech.chapter7.thread_exception_handler.StaticUncaughtExceptionHandler;

public class ThreadExceptionHandlerTest {
    public static void main(String[] args) {
        MyThreadGroup group = new MyThreadGroup("group");
        MyThread t = new MyThread(group, "t");
        t.setUncaughtExceptionHandler(new ObjectUncaughtExceptionHandler());
        MyThread.setDefaultUncaughtExceptionHandler(new StaticUncaughtExceptionHandler());
        t.start();
    }
}
