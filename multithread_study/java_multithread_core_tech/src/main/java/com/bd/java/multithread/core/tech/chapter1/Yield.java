package com.bd.java.multithread.core.tech.chapter1;

public class Yield extends Thread {
    @Override
    public void run() {
        long beginTime = System.currentTimeMillis();
        int count = 0;

        for(int i = 0; i < 5000; i++) {
            Thread.yield();
            count += i;
        }

        long endTime = System.currentTimeMillis();
        System.out.println("time " + (endTime - beginTime) + " ms");
    }
}
