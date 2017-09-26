package com.bd.java_multithread_core_tech.chapter2.thread6;

public class Task {
    //没有添加synchronized关键字，method会随机异步执行
    synchronized public void method() {
        System.out.println("method");
    }

    public void doLongTimeTask() {
        synchronized (this) {
            for (int i = 0; i < 1000; i++) {
                System.out.println("synchronized thread name = " + Thread.currentThread().getName() + " i = " + (i + 1));
            }
        }
    }
}
