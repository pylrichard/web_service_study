package com.bd.roncoo.multithread.sync.visibility;

public class VolatileTask2 {
    /**
     * volatile使变量在多个线程之间可见
     */
    public volatile boolean isRunning = false;

    public static void main(String[] args) {
        VolatileTask2 task = new VolatileTask2();
        new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            task.isRunning = true;
        }).start();
        new Thread(() -> {
            while (!task.isRunning) {
            }
            System.out.println(Thread.currentThread().getName() + "运行");
        }).start();
    }
}
