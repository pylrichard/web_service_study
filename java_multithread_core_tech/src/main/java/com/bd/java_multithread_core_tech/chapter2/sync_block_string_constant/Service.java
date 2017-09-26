package com.bd.java_multithread_core_tech.chapter2.sync_block_string_constant;

public class Service {
    public static void method(String string) {
        try {
            //JVM中有String常量池缓存，传入"AA"，则A和B线程持有同一个"AA"对象锁
            //此处执行结果是A线程一直执行，B线程不能执行
            //同步代码块不使用String作为锁，使用Object对象(不会放入缓存)
            synchronized (string) {
                while (true) {
                    System.out.println("thread name = " + Thread.currentThread().getName());
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
