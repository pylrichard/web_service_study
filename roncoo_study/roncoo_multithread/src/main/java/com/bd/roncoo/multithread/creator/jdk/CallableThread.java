package com.bd.roncoo.multithread.creator.jdk;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ������ֵ
 */
public class CallableThread implements Callable<Integer> {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableThread callableThread = new CallableThread();
        FutureTask<Integer> task = new FutureTask<>(callableThread);
        Thread thread = new Thread(task);
        thread.start();
        System.out.println("���߳�������");
        Integer result = task.get();
        System.out.println("���߳�ִ�н��Ϊ:" + result);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("call()ִ��");
        Thread.sleep(200);
        return 1;
    }
}
