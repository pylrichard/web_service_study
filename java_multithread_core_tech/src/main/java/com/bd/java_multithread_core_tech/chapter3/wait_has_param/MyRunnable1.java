package com.bd.java_multithread_core_tech.chapter3.wait_has_param;

public class MyRunnable1 {
    static private Object lock = new Object();

    static private Runnable runnable = new Runnable() {
        public void run() {
            try {
                synchronized (lock) {
                    System.out.println("wait begin");
                    //wait(long)在某一时间范围内等待其它线程进行唤醒，超时则自动唤醒
                    lock.wait(5000);
                    System.out.println("wait end");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) {
        Thread t = new Thread(runnable);
        t.start();
    }
}
