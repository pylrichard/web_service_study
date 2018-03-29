package com.bd.java.multithread.core.tech.chapter1;

public class ThreadInterrupt5Test {
    public static void main(String[] args) {
        ThreadInterrupt3 t = new ThreadInterrupt3();
        t.start();

        System.out.println("interrupted 1 = " + t.isInterrupted());
        System.out.println("in main() interrupt other chapter1");
        //先执行interrupt中断线程
        t.interrupt();
        //此时状态标志值为true
        System.out.println("interrupted 2 = " + t.isInterrupted());
        System.out.println("in main() leaving");
    }
}
