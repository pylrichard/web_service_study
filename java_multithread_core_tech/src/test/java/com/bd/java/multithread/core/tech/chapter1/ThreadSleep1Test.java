package com.bd.java.multithread.core.tech.chapter1;

import com.bd.java.multithread.core.tech.chapter1.ThreadSleep1;

public class ThreadSleep1Test {
    public static void main(String[] args) {
        ThreadSleep1 t = new ThreadSleep1();
        System.out.println("begin " + System.currentTimeMillis());
        t.run();
        System.out.println("end " + System.currentTimeMillis());
    }
}
