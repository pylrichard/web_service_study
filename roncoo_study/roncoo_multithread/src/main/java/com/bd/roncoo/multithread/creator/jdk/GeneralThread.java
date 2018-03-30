package com.bd.roncoo.multithread.creator.jdk;

/**
 * �̳�Thread��
 */
public class GeneralThread extends Thread {
    public GeneralThread(String name) {
        super(name);
    }

    public static void main(String[] args) {
        GeneralThread generalThread = new GeneralThread("1st-thread");
        GeneralThread thread2 = new GeneralThread("2nd-thread");
        generalThread.start();
        thread2.start();
        generalThread.interrupt();
    }

    @Override
    public void run() {
        while (!interrupted()) {
            System.out.println(getName() + "�߳�����");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
