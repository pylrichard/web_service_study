package com.bd.java_multithread_core_tech.chapter2.thread3;

public class Thread3Test {
    public static void main(String[] args) {
        try {
            PublicVar var = new PublicVar();

            ThreadA t = new ThreadA(var);
            t.start();
            //睡眠时间小于t线程睡眠时间
            Thread.sleep(200);
            //setValue有synchronized关键字，getValue没有添加synchronized关键字，出现脏读
            //getValue添加synchronized关键字，解决脏读
            var.getValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
