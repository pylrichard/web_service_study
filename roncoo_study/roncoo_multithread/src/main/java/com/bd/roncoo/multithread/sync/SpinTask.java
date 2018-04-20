package com.bd.roncoo.multithread.sync;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpinTask {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 6; i++) {
            threadPool.execute(SpinTask::run);
        }
        //不调用shutdown()，线程池的线程处于TIMED_WAITING状态
//        threadPool.shutdown();
        /*for (int i = 0; i < 6; i++) {
            new Thread(SpinTask::run).start();
        }*/
        /*
            jps获取进程号
            jstack 进程号 查看进程的线程信息
         */
        while (Thread.activeCount() != 2) {
            //线程池线程数为2
        }
        System.out.println("");
    }

    private static void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "开始执行");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "结束执行");
    }
}
