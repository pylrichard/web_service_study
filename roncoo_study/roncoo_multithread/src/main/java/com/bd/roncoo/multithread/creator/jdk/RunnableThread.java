package com.bd.roncoo.multithread.creator.jdk;

/**
 * ʵ��Runnable�ӿ�
 */
public class RunnableThread implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableThread());
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("�߳�����");
        }
    }
}
