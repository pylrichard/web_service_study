package com.bd.java_multithread_core_tech.chapter3.wait_demo;

public class WaitDemo1 {
    public static void main(String[] args) {
        try {
            String str = new String("");
            //调用wait()之前，线程必须获得对象锁，即只能在同步方法/同步代码块中调用wait()
            str.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
