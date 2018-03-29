package com.bd.java.multithread.core.tech.chapter1;

public class MainThread {
    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        System.out.println("name = " + t.getName() + " id = " + t.getId());
    }
}
