package com.bd.java_multithread_core_tech.chapter2.volatile_sync;

public class VolatileSyncTest {
    private static int THREAD_NUM = 100;

    public static void main(String[] args) {
        MyThread[] group = new MyThread[THREAD_NUM];

        for (int i = 0; i < THREAD_NUM; i++) {
            group[i] = new MyThread();
        }

        for (int i = 0; i < THREAD_NUM; i++) {
            group[i].start();
        }
    }
}
