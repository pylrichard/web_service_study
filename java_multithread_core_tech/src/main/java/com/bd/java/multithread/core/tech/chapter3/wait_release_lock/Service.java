package com.bd.java.multithread.core.tech.chapter3.wait_release_lock;

public class Service {
    public void method(Object lock) {
        try {
            synchronized (lock) {
                System.out.println("sync begin");
                //wait()执行后锁被自动释放
                lock.wait();

                //sleep()不会释放锁
                //Thread.sleep(4000);
                System.out.println("sync end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
