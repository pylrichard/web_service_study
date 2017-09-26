package com.bd.java_multithread_core_tech.chapter7.thread_run_in_order;

public class MyThread extends Thread {
    private static int THREAD_NUM = 3;
    private static int RUN_COUNT = 5;
    private Object lock;
    //本线程的执行顺序索引，控制运行顺序
    private int order;
    //本线程的运行次数，本线程私有，控制运行次数
    private int runCount = 0;
    //所有线程运行总次数，所有线程都可以访问
    volatile private static int totalRunCount = 1;

    public MyThread(Object lock, int order) {
        super();
        this.lock = lock;
        this.order = order;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                while (true) {
                    //到达本线程的执行顺序索引时执行线程逻辑
                    if (totalRunCount % THREAD_NUM == order) {
                        System.out.println("thread " + Thread.currentThread().getName() + " runCount " + totalRunCount);
                        //唤醒所有等待线程来获取锁，只有满足执行顺序索引的线程才能获取
                        //注意必须是notifyAll，否则可能唤醒到的线程不满足执行顺序索引，所有线程都进入等待，呈假死状态
                        lock.notifyAll();
                        totalRunCount++;
                        runCount++;
                        if (runCount == RUN_COUNT) {
                            break;
                        }
                    } else {
                        lock.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
