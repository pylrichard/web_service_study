package com.bd.roncoo.multithread.creator.jdk;

/**
 * 匿名内部类
 */
public class InnerThread {
    public static void main(String[] args) {
        /*new Thread() {
            public void run() {
                System.out.println("线程运行");
            }
        }.start();*/

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程运行");
            }
        }).start();*/

        /*
            此处执行Thread run()，见Thread.run()调用target.run()
            多态调用子类方法
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
