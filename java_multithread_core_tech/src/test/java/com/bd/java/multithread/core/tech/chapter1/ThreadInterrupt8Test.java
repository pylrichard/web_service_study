package com.bd.java.multithread.core.tech.chapter1;

import com.bd.java.multithread.core.tech.chapter1.ThreadInterrupt8;

public class ThreadInterrupt8Test {
    public static void main(String[] args) throws InterruptedException {
        ThreadInterrupt8 t = new ThreadInterrupt8();
        t.start();
        Thread.sleep(1000);
        t.interrupt();
    }
}
