package com.bd.roncoo.multithread.creator.jdk;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ������ֵ
 */
public class Thread4 implements Callable<Integer> {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread4 thread4 = new Thread4();
        FutureTask<Integer> task = new FutureTask<>(thread4);
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
