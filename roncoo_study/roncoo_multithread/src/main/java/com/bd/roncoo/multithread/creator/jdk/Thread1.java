package com.bd.roncoo.multithread.creator.jdk;

/**
 * 继承Thread类
 */
public class Thread1 extends Thread {
    public Thread1(String name) {
        super(name);
    }

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1("1st-thread");
        Thread1 thread2 = new Thread1("2nd-thread");
        thread1.start();
        thread2.start();
        thread1.interrupt();
    }

    @Override
    public void run() {
        while (!interrupted()) {
            System.out.println(getName() + "线程运行");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
