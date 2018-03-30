package com.bd.roncoo.multithread.creator.jdk;

/**
 * �����ڲ���
 */
public class InnerThread {
    public static void main(String[] args) {
        /*new Thread() {
            public void run() {
                System.out.println("�߳�����");
            }
        }.start();*/

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("�߳�����");
            }
        }).start();*/

        /*
            �˴�ִ��Thread run()����Thread.run()����target.run()
            ��̬�������෽��
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable run()");
            }
        }) {
            public void run() {
                System.out.println("Thread run()");
            }
        }.start();
    }
}
