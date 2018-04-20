package com.bd.roncoo.multithread.creator.jdk;

/**
 * 不带返回值的创建方式
 */
public class RunnableTask implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableTask());
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("run()");
        }
    }
}
