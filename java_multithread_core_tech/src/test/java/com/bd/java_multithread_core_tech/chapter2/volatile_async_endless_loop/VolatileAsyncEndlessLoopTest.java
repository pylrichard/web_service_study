package com.bd.java_multithread_core_tech.chapter2.volatile_async_endless_loop;

public class VolatileAsyncEndlessLoopTest {
    public static void main(String[] args) {
        try {
            ThreadA t = new ThreadA();
            t.start();
            Thread.sleep(1000);
            t.setRunning(false);
            System.out.println("isRunning = false");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
