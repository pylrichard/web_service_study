package com.bd.roncoo.multithread.creator.jdk;

/**
 * 实现Runnable接口
 */
public class RunnableThread implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableThread());
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("线程运行");
        }
    }
}
