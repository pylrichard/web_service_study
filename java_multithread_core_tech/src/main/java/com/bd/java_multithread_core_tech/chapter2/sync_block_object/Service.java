package com.bd.java_multithread_core_tech.chapter2.sync_block_object;

public class Service {
    public static void method(Object object) {
        try {
            //执行结果是A和B线程交替运行，持有的锁不是同一个对象
            synchronized (object) {
                while (true) {
                    System.out.println("thread name = " + Thread.currentThread().getName());
                    Thread.sleep(1500);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
