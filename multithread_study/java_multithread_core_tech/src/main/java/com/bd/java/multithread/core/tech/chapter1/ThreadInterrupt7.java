package com.bd.java.multithread.core.tech.chapter1;

public class ThreadInterrupt7 extends Thread{
    @Override
    public void run() {
        try {
            this.stop();
        } catch (ThreadDeath e) {
            System.out.println("ThreadDeath catch");
            e.printStackTrace();
        }
    }
}
