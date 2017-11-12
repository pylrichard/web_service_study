package com.bd.java.multithread.core.tech.chapter2.thread1;

import com.bd.java.multithread.core.tech.chapter2.thread1.HasSelfPrivateNum;
import com.bd.java.multithread.core.tech.chapter2.thread1.ThreadA;
import com.bd.java.multithread.core.tech.chapter2.thread1.ThreadB;

public class Thread1Test {
    public static void main(String[] args) {
        HasSelfPrivateNum numRef = new HasSelfPrivateNum();
        ThreadA t1 = new ThreadA(numRef);
        t1.start();
        ThreadB t2 = new ThreadB(numRef);
        t2.start();
    }
}
