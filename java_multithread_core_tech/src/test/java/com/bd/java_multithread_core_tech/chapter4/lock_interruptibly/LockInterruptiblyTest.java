package com.bd.java_multithread_core_tech.chapter4.lock_interruptibly;

public class LockInterruptiblyTest {
    public static void main(String[] args) throws InterruptedException {
        final MyService service = new MyService();
        Runnable runnable = new Runnable() {
            public void run() {
                service.waitMethod();
            }
        };

        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        t1.start();
        Thread.sleep(1000);
        Thread t2 = new Thread(runnable);
        t2.setName("t2");
        t2.start();
        //设置中断标志为true，并不会立刻中断线程
        t2.interrupt();
        System.out.println("t2 isAlive " + t2.isAlive());
        System.out.println("main end");
    }
}
