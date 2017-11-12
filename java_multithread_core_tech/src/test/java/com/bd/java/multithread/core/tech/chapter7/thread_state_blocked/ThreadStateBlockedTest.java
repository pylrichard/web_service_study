package com.bd.java.multithread.core.tech.chapter7.thread_state_blocked;

import com.bd.java.multithread.core.tech.chapter7.thread_state_blocked.ThreadA;
import com.bd.java.multithread.core.tech.chapter7.thread_state_blocked.ThreadB;

public class ThreadStateBlockedTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadA t1 = new ThreadA();
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB();
        t2.setName("t2");
        t2.start();
        //此处需要休眠1s，否则t1和t2的状态可能都是RUNNABLE，打印的状态是随机的
        Thread.sleep(1000);
        System.out.println("t1 thread state " + t1.getState());
        System.out.println("t2 thread state " + t2.getState());
    }
}
