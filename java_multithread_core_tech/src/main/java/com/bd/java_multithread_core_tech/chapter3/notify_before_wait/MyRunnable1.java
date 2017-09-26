package com.bd.java_multithread_core_tech.chapter3.notify_before_wait;

public class MyRunnable1 {
    private Object lock = new Object();

    private Runnable runnable1 = new Runnable() {
        public void run() {
            try {
                synchronized (lock) {
                    System.out.println("wait begin");
                    lock.wait();
                    System.out.println("wait end");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable runnable2 = new Runnable() {
        public void run() {
            synchronized (lock) {
                System.out.println("notify begin");
                lock.notify();
                System.out.println("notify end");
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        MyRunnable1 myRunnable = new MyRunnable1();
        /*
        Thread t1 = new Thread(myRunnable.runnable1);
        t1.start();
        Thread t2 = new Thread(myRunnable.runnable2);
        t2.start();
        */

        //先执行notify()，则wait()不会被通知
        Thread t2 = new Thread(myRunnable.runnable2);
        t2.start();
        Thread.sleep(100);
        Thread t1 = new Thread(myRunnable.runnable1);
        t1.start();
    }
}
