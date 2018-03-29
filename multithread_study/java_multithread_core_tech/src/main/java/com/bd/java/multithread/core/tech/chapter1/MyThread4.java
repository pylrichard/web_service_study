package com.bd.java.multithread.core.tech.chapter1;

public class MyThread4 extends Thread {
    private int count = 5;

    public MyThread4(String name) {
        super();
        this.setName(name);
    }

    @Override
    public void run() {
        super.run();

        while(count > 0) {
            count--;
            //println
            System.out.println("run = " + this.currentThread().getName() + " count: " + count);
        }
    }
}
