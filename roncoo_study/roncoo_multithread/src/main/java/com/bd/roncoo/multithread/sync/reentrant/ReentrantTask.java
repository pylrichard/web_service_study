package com.bd.roncoo.multithread.sync.reentrant;

public class ReentrantTask {
    public static void main(String[] args) {
        ReentrantTask task1 = new ReentrantTask();
        ReentrantTask task2 = new ReentrantTask();
        new Thread(task1::method1).start();
        new Thread(task2::method2).start();
    }

    private synchronized void method1() {
        System.out.println("method1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("method1 call method2");
        method2();
    }

    private synchronized void method2() {
        System.out.println("method2");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
