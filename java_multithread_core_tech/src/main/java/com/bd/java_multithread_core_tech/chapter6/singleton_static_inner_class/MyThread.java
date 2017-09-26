package com.bd.java_multithread_core_tech.chapter6.singleton_static_inner_class;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(MyService.getInstance().hashCode());
    }
}
