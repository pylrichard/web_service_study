package com.bd.java_multithread_core_tech.chapter1;

public class ThreadSleep1Test {
    public static void main(String[] args) {
        ThreadSleep1 t = new ThreadSleep1();
        System.out.println("begin " + System.currentTimeMillis());
        t.run();
        System.out.println("end " + System.currentTimeMillis());
    }
}
