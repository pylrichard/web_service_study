package com.bd.java_multithread_core_tech.chapter2.volatile_sync;

//可以删除volatile和synchronized关键字观察效果
//见2.3.4-*.png
public class MyThread extends Thread {
    volatile public static int count;

    //synchronized和static关键字锁定的是MyThread.class类，对所有MyThread对象都起到同步的作用
    //此处修改的是类静态变量，所有MyThread对象都可以访问
    synchronized private static void addCount() {
        for (int i = 0; i < 100; i++) {
            count++;
        }

        System.out.println("count = " + count);
    }

    @Override
    public void run() {
        addCount();
    }
}
