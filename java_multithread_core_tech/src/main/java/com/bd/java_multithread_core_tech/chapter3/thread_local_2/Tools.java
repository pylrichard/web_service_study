package com.bd.java_multithread_core_tech.chapter3.thread_local_2;

public class Tools {
    public static ThreadLocal tl = new ThreadLocal();

    public static void setTL(Object obj) {
        tl.set(obj);
    }

    public static Object getTL() {
        return tl.get();
    }
}
