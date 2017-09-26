package com.bd.java_multithread_core_tech.chapter2.atomic_integer;

public class AtomicIntegerTest {
    private static int THREAD_NUM = 5;

    public static void main(String[] args) {
        Task task = new Task();
        Thread[] group = new Thread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i++) {
            group[i] = new Thread(task);
            group[i].start();
        }
    }
}
