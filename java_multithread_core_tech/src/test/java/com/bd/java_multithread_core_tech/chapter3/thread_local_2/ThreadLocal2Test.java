package com.bd.java_multithread_core_tech.chapter3.thread_local_2;

public class ThreadLocal2Test {
    public static void main(String[] args) {
        try {
            ThreadA ta = new ThreadA();
            ThreadB tb = new ThreadB();
            ta.start();
            tb.start();

            for (int i = 0; i < 5; i++) {
                Tools.setTL("main " + (i + 1));
                System.out.println("main get value = " + Tools.getTL());
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
