package com.bd.java.multithread.core.tech.chapter1;

public class ThreadInterrupt5 extends Thread {
    public void run() {
        try {
            for (int i = 0; i < 100000; i++) {
                System.out.println("i = " + i);
            }
            //执行完以上逻辑后，进入Thread.sleep()，被中断，进入catch语句
            System.out.println("cur " + this.currentThread().getName());
            System.out.println("in run() sleep for 20s");
            Thread.sleep(20000);
            System.out.println("in run() woke up");
        } catch (InterruptedException e) {
            //在sleep状态下中断一个线程，进入catch语句，并设置状态标志为false
            System.out.println("in run() interrupted " + this.isInterrupted() + " while sleeping");
            //没有return，线程不会实际被中断，下面语句会执行
            return;
        }

        System.out.println("in run() leaving");
    }
}
