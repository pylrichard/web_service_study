package com.bd.java_multithread_core_tech.chapter4.has_queued_thread;

public class HasQueuedThreadTest {
    public static void main(String[] args) {
        final MyService service = new MyService();
        Runnable runnable = new Runnable() {
            public void run() {
                service.waitMethod();
            }
        };

        Thread t1 = new Thread(runnable);
        t1.start();
        Thread t2 = new Thread(runnable);
        t2.start();

        //指定线程是否在等待锁
        System.out.println(service.getLock().hasQueuedThread(t1));
        System.out.println(service.getLock().hasQueuedThread(t2));
        //是否有线程在等待锁
        System.out.println(service.getLock().hasQueuedThreads());
    }
}
