package com.bd.java.multithread.core.tech.chapter2.volatile_sync;

import com.bd.java.multithread.core.tech.chapter2.volatile_sync.MyThread;

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
