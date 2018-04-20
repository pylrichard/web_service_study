package com.bd.roncoo.multithread.creator.jdk;

/**
 * 内部类创建
 */
public class InnerThread {
    public static void main(String[] args) {
        /*
            执行Thread run()，Thread.run()中包含target.run()
            多态情况下执行子类方法
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
