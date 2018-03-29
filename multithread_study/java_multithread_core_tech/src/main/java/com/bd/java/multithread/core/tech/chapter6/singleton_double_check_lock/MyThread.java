package com.bd.java.multithread.core.tech.chapter6.singleton_double_check_lock;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(MyService.getInstance().hashCode());
    }
}
