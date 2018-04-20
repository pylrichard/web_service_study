package com.bd.roncoo.multithread.creator.jdk;

/**
 * 通用线程创建方式
 */
public class GeneralThread extends Thread {
    public GeneralThread(String name) {
        super(name);
    }

    public static void main(String[] args) {
        GeneralThread thread1 = new GeneralThread("1st-thread");
        GeneralThread thread2 = new GeneralThread("2nd-thread");
        thread1.start();
        thread2.start();
        thread1.interrupt();
    }

    @Override
    public void run() {
        while (!interrupted()) {
            System.out.println(getName() + "正在运行");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
