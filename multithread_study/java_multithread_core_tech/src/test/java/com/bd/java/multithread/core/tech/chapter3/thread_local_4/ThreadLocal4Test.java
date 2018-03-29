package com.bd.java.multithread.core.tech.chapter3.thread_local_4;

public class ThreadLocal4Test {
    public static ThreadLocalExt tl = new ThreadLocalExt();

    public static void main(String[] args) {
        if (tl.get() == null) {
            System.out.println("tl = null");
            tl.set("pyl");
        }

        System.out.println(tl.get());
    }
}
