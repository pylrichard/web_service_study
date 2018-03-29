package com.bd.java.multithread.core.tech.chapter1.suspend_resume;

public class SyncObject {
    synchronized public void printString() {
        System.out.println("begin");
        if(Thread.currentThread().getName().equals("t1")) {
            System.out.println("chapter1 t1 suspend");
            Thread.currentThread().suspend();
        }
        System.out.println("end");
    }
}
