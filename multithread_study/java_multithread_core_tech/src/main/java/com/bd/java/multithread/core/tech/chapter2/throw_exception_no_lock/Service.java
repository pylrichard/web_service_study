package com.bd.java.multithread.core.tech.chapter2.throw_exception_no_lock;

public class Service {
    synchronized public void method() {
        String name = Thread.currentThread().getName();
        System.out.println("thread name = " + name);

        if (name.equals("t1")) {
            try {
                Thread.sleep(100);
                //t1线程执行到此出发异常，释放对象锁，t2线程可以执行此同步方法
                Integer.parseInt("t1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
