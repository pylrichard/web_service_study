package com.bd.java.multithread.core.tech.chapter1;

import com.bd.java.multithread.core.tech.chapter1.ThreadInterrupt3;

public class ThreadInterrupt3Test {
    public static void main(String[] args) {
        ThreadInterrupt3 t = new ThreadInterrupt3();
        t.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("interrupted 1 = " + t.isInterrupted());
        System.out.println("in main() interrupt other chapter1");
        t.interrupt();
        //此时状态标志值为true
        System.out.println("interrupted 2 = " + t.isInterrupted());
        System.out.println("in main() leaving");
    }
}
