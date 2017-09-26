package com.bd.java_multithread_core_tech.chapter3.wait_interrupt_exception;

public class Service {
    public void method(Object lock) {
        try {
            synchronized (lock) {
                System.out.println("wait begin");
                lock.wait();
                System.out.println("wait end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("catch InterruptedException");
        }
    }
}
