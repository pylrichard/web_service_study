package com.bd.roncoo.multithread.sync;

public class WaitNotifyTask implements Runnable {
    public static void main(String[] args) {
        WaitNotifyTask task = new WaitNotifyTask();
        Thread thread = new Thread(task);
        thread.start();
        while (true) {
            synchronized (task) {
                System.out.println("休眠1s后唤醒等待线程");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.notifyAll();
            }
        }
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("WaitNotifyTask恢复执行");
        }
    }
}
