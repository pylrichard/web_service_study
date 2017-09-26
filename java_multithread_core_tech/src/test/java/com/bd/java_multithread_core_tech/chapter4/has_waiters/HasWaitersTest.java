package com.bd.java_multithread_core_tech.chapter4.has_waiters;

public class HasWaitersTest {
    private static int THREAD_NUM = 10;

    public static void main(String[] args) throws InterruptedException {
        final MyService service = new MyService();
        Runnable runnable = new Runnable() {
            public void run() {
                service.waitMethod();
            }
        };

        Thread[] group = new Thread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i++) {
            group[i] = new Thread(runnable);
            group[i].start();
        }

        Thread.sleep(2000);
        service.notifyMethod();
    }
}
