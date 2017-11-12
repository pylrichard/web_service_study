package com.bd.java.multithread.core.tech.chapter1;

import com.bd.java.multithread.core.tech.chapter1.ThreadInterrupt2;

public class ThreadInterrupt2Test {
    public static void main(String[] args) {
        try {
            ThreadInterrupt2 t = new ThreadInterrupt2();
            t.start();
            Thread.sleep(10);
            t.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
