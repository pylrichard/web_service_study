package com.bd.java.multithread.core.tech.chapter4.reentrant_lock_fair_nofair;

public class ReentrantLockFairNoFairTest {
    private static int THREAD_NUM = 10;

    public static void main(String[] args) {
        final MyService service = new MyService(false);

        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " running");
                service.method();
            }
        };

        Thread[] group = new Thread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i++) {
            group[i] = new Thread(runnable);
            group[i].start();
        }
    }
}
