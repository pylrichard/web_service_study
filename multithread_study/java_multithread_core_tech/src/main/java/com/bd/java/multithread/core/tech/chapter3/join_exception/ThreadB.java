package com.bd.java.multithread.core.tech.chapter3.join_exception;

public class ThreadB extends Thread {
    @Override
    public void run() {
        try {
            ThreadA t = new ThreadA();
            t.start();
            t.join();
            System.out.println("run end");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("catch end");
        }
    }
}
