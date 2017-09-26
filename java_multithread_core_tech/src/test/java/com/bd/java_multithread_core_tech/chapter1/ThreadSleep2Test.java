package com.bd.java_multithread_core_tech.chapter1;

public class ThreadSleep2Test {
    public static void main(String[] args) {
        ThreadSleep2 t = new ThreadSleep2();
        System.out.println("begin " + System.currentTimeMillis());
        //main线程和t线程是异步执行的
        t.start();
        System.out.println("end " + System.currentTimeMillis());
    }
}
