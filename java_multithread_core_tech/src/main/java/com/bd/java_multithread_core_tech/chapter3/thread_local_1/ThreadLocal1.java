package com.bd.java_multithread_core_tech.chapter3.thread_local_1;

public class ThreadLocal1 {
    public static ThreadLocal tl = new ThreadLocal();

    public static void main(String[] args) {
        if (tl.get() == null) {
            System.out.println("tl = null");
            tl.set("pyl");
        }

        System.out.println(tl.get());
    }
}
