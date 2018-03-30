package com.bd.roncoo.multithread.creator.jdk;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 带返回值
 */
public class CallableThread implements Callable<Integer> {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableThread callableThread = new CallableThread();
        FutureTask<Integer> task = new FutureTask<>(callableThread);
        Thread thread = new Thread(task);
        thread.start();
        System.out.println("子线程已启动");
        Integer result = task.get();
        System.out.println("子线程执行结果为:" + result);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("call()执行");
        Thread.sleep(200);
        return 1;
    }
}
