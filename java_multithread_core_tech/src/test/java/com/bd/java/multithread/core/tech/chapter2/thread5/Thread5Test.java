package com.bd.java.multithread.core.tech.chapter2.thread5;

import com.bd.java.multithread.core.tech.chapter2.thread5.Task;
import com.bd.java.multithread.core.tech.chapter2.thread5.ThreadA;
import com.bd.java.multithread.core.tech.chapter2.thread5.ThreadB;

public class Thread5Test {
    public static void main(String[] args) {
        Task task = new Task();
        ThreadA t1 = new ThreadA(task);
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB(task);
        t2.setName("t2");
        t2.start();
    }
}
