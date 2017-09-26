package com.bd.java_multithread_core_tech.chapter3.inheritable_thread_local;

public class Tools {
    public static InheritableThreadLocalExt itl = new InheritableThreadLocalExt();

    public static void setTL(Object obj) {
        itl.set(obj);
    }

    public static Object getTL() {
        return itl.get();
    }
}
