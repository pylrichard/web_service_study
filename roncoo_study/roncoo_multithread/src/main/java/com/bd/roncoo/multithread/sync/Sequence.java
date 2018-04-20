package com.bd.roncoo.multithread.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sequence {
    private static int value;

    public static void main(String[] args) {
        Sequence sequence = new Sequence();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 6; i++) {
            threadPool.execute(() -> {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " " + sequence.getNext());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }

    /**
     * synchronized修饰普通方法，锁住当前类对象
     */
    public synchronized int getNext() {
        //value++不是原子性操作
        return value++;
    }

    /**
     * synchronized修饰静态方法，锁住当前Class字节码对象
     */
    public static synchronized int getPrevious() {
        return value--;
    }

    public int getValue() {
        //JVM添加monitorenter指令
        synchronized (Sequence.class) {
            if (value > 0) {
                return value;
            } else {
                return -1;
            }
        }
        //JVM添加monitorexit指令
    }
}
