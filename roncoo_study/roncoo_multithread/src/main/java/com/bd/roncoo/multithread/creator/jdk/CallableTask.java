package com.bd.roncoo.multithread.creator.jdk;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 带返回值的创建方式
 */
public class CallableTask implements Callable<Integer> {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableTask callableTask = new CallableTask();
        FutureTask<Integer> task = new FutureTask<>(callableTask);
        Thread thread = new Thread(task);
        thread.start();
        System.out.println("子线程开始执行");
        Integer result = task.get();
        System.out.println("执行结果:" + result);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("执行call()");
        Thread.sleep(200);
        return 1;
    }
}
