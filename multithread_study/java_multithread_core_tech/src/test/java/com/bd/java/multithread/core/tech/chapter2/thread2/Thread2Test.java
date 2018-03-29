package com.bd.java.multithread.core.tech.chapter2.thread2;

public class Thread2Test {
    public static void main(String[] args) {
        HasSelfPrivateNum numRef = new HasSelfPrivateNum();
        //同步访问，a先启动，先打印a
        ThreadA t1 = new ThreadA(numRef);
        t1.start();
        ThreadB t2 = new ThreadB(numRef);
        t2.start();
    }
}
