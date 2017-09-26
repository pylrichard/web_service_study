package com.bd.java_multithread_core_tech.chapter3.inheritable_thread_local;

public class InheritableThreadLocalTest {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("main get value = " + Tools.getTL());
                Thread.sleep(100);
            }

            Thread.sleep(2000);
            ThreadA ta = new ThreadA();
            ta.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
