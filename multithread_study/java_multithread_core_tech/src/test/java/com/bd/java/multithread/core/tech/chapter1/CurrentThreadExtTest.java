package com.bd.java.multithread.core.tech.chapter1;

public class CurrentThreadExtTest {
    public static void main(String[] args) {
        try {
            CurrentThreadExt t1 = new CurrentThreadExt();
            t1.start();
            //睡眠1s后t1已经执行完毕
            Thread.sleep(1000);
            System.out.println("after start t1 isAlive " + t1.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //CurrentThreadExt t1 = new CurrentThreadExt();
        //t1.start();
        //t1.run();

        /*
        Thread t2 = new Thread(t1);
        System.out.println("begin start t2 isAlive " + t2.isAlive());
        t2.setName("t2");
        t2.start();
        System.out.println("after start t2 isAlive " + t2.isAlive());
        */
    }
}
