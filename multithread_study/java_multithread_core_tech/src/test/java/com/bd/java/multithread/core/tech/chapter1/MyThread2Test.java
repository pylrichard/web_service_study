package com.bd.java.multithread.core.tech.chapter1;

public class MyThread2Test {
    public static void main(String[] args) {
        try {
            MyThread2 t = new MyThread2();
            t.setName("MyThread");
            t.start();

            for(int i = 0; i < 10; i++) {
                int time = (int)(Math.random() * 1000);
                Thread.sleep(time);
                System.out.println("main = " + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
