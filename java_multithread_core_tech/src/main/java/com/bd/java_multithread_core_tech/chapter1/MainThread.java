package com.bd.java_multithread_core_tech.chapter1;

public class MainThread {
    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        System.out.println("name = " + t.getName() + " id = " + t.getId());
    }
}
