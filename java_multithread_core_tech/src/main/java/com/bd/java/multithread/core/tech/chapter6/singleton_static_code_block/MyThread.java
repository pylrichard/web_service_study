package com.bd.java.multithread.core.tech.chapter6.singleton_static_code_block;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(MyService.getInstance().hashCode());
    }
}