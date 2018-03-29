package com.bd.java.multithread.core.tech.chapter3.thread_local_4;

public class ThreadLocalExt extends ThreadLocal {
    @Override
    protected Object initialValue() {
        return "default value";
    }
}
