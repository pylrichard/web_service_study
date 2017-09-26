package com.bd.java_multithread_core_tech.chapter1;

public class ThreadInterrupt2 extends Thread {
    @Override
    public void run() {
        super.run();

        for(int i = 0; i < 500; i++) {
            if(this.isInterrupted()) {
                System.out.println("cur " + this.currentThread().getName());
                System.out.println("interrupted, exit");
                break;
            }

            System.out.println("i = " + i);
        }

        System.out.println("chapter1 don't stop");
    }
}
