package com.bd.roncoo.multithread.creator.jdk;

/**
 * 实现Runnable接口
 */
public class Thread2 implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new Thread2());
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("线程运行");
        }
    }
}
