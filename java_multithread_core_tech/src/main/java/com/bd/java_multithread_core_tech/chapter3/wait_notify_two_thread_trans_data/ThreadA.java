package com.bd.java_multithread_core_tech.chapter3.wait_notify_two_thread_trans_data;

public class ThreadA extends Thread {
    private Object lock;

    public ThreadA(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                if (Service.getSize() != 5) {
                    String name = Thread.currentThread().getName();
                    System.out.println(name + " wait begin");
                    lock.wait();
                    System.out.println(name + " wait end");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
