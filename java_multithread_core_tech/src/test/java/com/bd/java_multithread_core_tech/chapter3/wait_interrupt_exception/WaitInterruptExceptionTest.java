package com.bd.java_multithread_core_tech.chapter3.wait_interrupt_exception;

public class WaitInterruptExceptionTest {
    public static void main(String[] args) {
        try {
            Object lock = new Object();
            MyThread t = new MyThread(lock);
            t.start();
            Thread.sleep(3000);
            t.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
