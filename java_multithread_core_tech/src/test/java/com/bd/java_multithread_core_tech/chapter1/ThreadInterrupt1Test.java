package com.bd.java_multithread_core_tech.chapter1;

public class ThreadInterrupt1Test {
    public static void main(String[] args) {
        try {
            ThreadInterrupt1 t = new ThreadInterrupt1();
            t.start();
            Thread.sleep(10);
            //设置中断标志为true，并不会立刻中断线程
            t.interrupt();

            System.out.println("cur " + t.currentThread().getName());
            //interrupted是类静态方法，判断当前线程的中断情况，当前线程是main线程，即运行t.interrupt()的线程
            //main线程并没有被中断，所以输出结果为false
            //System.out.println("interrupted 1 = " + t.interrupted());
            //System.out.println("interrupted 2 = " + t.interrupted());

            //isInterrupted是类实例方法
            //main线程睡眠时间为10，main线程被唤醒时对t线程设置中断标志为true，所以输出结果为true
            //main线程睡眠时间为1000，main线程被唤醒时t线程已经消亡，所以输出结果为false
            System.out.println("t isAlive " + t.isAlive());
            System.out.println("interrupted 1 = " + t.isInterrupted());
            System.out.println("interrupted 2 = " + t.isInterrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
        System.out.println("interrupted 1 = " + Thread.currentThread().isInterrupted());
        Thread.currentThread().interrupt();
        //线程中断状态由interrupted清除，连续两次调用该方法，第二次调用返回false
        System.out.println("interrupted 2 = " + Thread.currentThread().isInterrupted());
        */

        /*
        //连续两次调用interrupted()，第二次调用返回false
        System.out.println("interrupted 1 = " + t.interrupted());
        System.out.println("interrupted 2 = " + t.interrupted());
        */
    }
}
