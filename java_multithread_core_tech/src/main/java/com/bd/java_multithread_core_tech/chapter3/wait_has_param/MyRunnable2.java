package com.bd.java_multithread_core_tech.chapter3.wait_has_param;

public class MyRunnable2 {
    static private Object lock = new Object();

    static private Runnable runnable1 = new Runnable() {
        public void run() {
            try {
                synchronized (lock) {
                    System.out.println("wait begin");
                    lock.wait(4000);
                    System.out.println("wait end");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    static private Runnable runnable2 = new Runnable() {
        public void run() {
            synchronized (lock) {
                System.out.println("notify begin");
                //wait(long)可被其它线程在指定时间范围内唤醒
                lock.notify();
                System.out.println("notify end");
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(runnable1);
        t1.start();
        Thread.sleep(2000);
        Thread t2 = new Thread(runnable2);
        t2.start();
    }
}
