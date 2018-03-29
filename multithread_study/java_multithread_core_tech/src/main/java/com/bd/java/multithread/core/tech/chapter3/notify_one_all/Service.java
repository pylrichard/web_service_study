package com.bd.java.multithread.core.tech.chapter3.notify_one_all;

public class Service {
    public void waitMethod(Object lock) {
        try {
            synchronized (lock) {
                String name = Thread.currentThread().getName();
                System.out.println(name + " wait begin");
                lock.wait();
                System.out.println(name + " wait end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
