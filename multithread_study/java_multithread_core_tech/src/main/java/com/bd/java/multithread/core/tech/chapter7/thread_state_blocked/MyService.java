package com.bd.java.multithread.core.tech.chapter7.thread_state_blocked;

public class MyService {
    synchronized public static void method() {
        try {
            System.out.println(Thread.currentThread().getName() + " method");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
