package com.bd.java.multithread.core.tech.chapter7.uncaught_exception_handler;

public class UncaughtExceptionHandlerTest {
    public static void main(String[] args) {
        //为指定线程类的所有线程对象设置默认异常处理器
        MyThread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                System.out.println(thread.getName() + " throw exception");
                throwable.printStackTrace();
            }
        });
        MyThread t1 = new MyThread();
        t1.setName("t1");
        //为指定线程对象设置异常处理器
        t1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                System.out.println(thread.getName() + " throw exception");
                throwable.printStackTrace();
            }
        });
        t1.start();

        MyThread t2 = new MyThread();
        t2.setName("t2");
        t2.start();
    }
}
