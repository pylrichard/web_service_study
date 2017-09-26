package com.bd.java_multithread_core_tech.chapter3.join_sleep;

public class JoinSleepTest {
    public static void main(String[] args) {
        try {
            MyThread t = new MyThread();
            t.start();
            //希望t线程执行后完毕后再执行main线程之后的语句，此处使用Thread.sleep不知道需要休眠多少秒
            //Thread.sleep(?);
            //使用join让main线程无限期阻塞，等待t线程销毁后再继续运行main线程之后的代码
            t.join();
            System.out.println("main end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
