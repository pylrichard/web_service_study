package com.bd.java.multithread.core.tech.chapter6.singleton.static_code.block;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(MyService.getInstance().hashCode());
    }
}
