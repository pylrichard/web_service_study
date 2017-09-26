package com.bd.java_multithread_core_tech.chapter2.thread4;

public class Task {
    private String data1;
    private String data2;

    public synchronized void doLongTimeTaskSync() {
        try {
            String name = Thread.currentThread().getName();
            System.out.println("thread name = " + name + " begin task");
            Thread.sleep(1000);
            data1 = "data1 thread name = " + name;
            data2 = "data2 thread name = " + name;
            System.out.println(data1);
            System.out.println(data2);
            System.out.println("thread name = " + name + " end task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doLongTimeTask() {
        try {
            //局部变量在不同线程之间是否是共享的？
            //String name = Thread.currentThread().getName();
            System.out.println("thread name = " + Thread.currentThread().getName() + " begin task");

            Thread.sleep(1000);
            String localData1 = "data1 thread name = " + Thread.currentThread().getName();
            String localData2 = "data2 thread name = " + Thread.currentThread().getName();
            //当一个线程访问对象的同步代码块时，另一个线程可以访问该对象的非同步代码块
            synchronized (this) {
                data1 = localData1;
                data2 = localData2;
            }
            System.out.println(data1);
            System.out.println(data2);
            System.out.println("thread name = " + Thread.currentThread().getName() + " end task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
