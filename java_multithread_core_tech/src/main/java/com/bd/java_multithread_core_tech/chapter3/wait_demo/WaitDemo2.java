package com.bd.java_multithread_core_tech.chapter3.wait_demo;

public class WaitDemo2 {
    public static void main(String[] args) {
        try {
            String lock = new String();
            System.out.println("before sync");
            synchronized (lock) {
                System.out.println("before wait");
                lock.wait();
                //wait()之后的代码不会运行
                System.out.println("after wait");
            }
            System.out.println("after sync");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
