package com.bd.roncoo.multithread.sync;

public class DeadLockTask {
    private Object obj1 = new Object();
    private Object obj2 = new Object();

    public static void main(String[] args) {
        DeadLockTask task = new DeadLockTask();
        new Thread(task::method1).start();
        new Thread(task::method2).start();
    }

    public void method1() {
        synchronized (obj1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("obj1 method1");
            synchronized (obj2) {
                System.out.println("obj2 method1");
            }
        }
    }

    public void method2() {
        synchronized (obj2) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("obj2 method2");
            synchronized (obj1) {
                System.out.println("obj1 method2");
            }
        }
    }
}
