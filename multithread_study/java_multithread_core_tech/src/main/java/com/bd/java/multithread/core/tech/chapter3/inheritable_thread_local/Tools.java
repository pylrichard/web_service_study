package com.bd.java.multithread.core.tech.chapter3.inheritable_thread_local;

public class Tools {
    public static InheritableThreadLocalExt itl = new InheritableThreadLocalExt();

    public static void setTL(Object obj) {
        itl.set(obj);
    }

    public static Object getTL() {
        return itl.get();
    }
}
