package com.bd.java.multithread.core.tech.chapter1;

public class DaemonThreadTest {
    public static void main(String[] args) {
        try {
            ThreadInterrupt6 t = new ThreadInterrupt6();
            t.setDaemon(true);
            t.start();
            Thread.sleep(5000);
            //main线程消亡，守护线程也跟着消亡
            System.out.println("main end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
