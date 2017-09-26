package com.bd.java_multithread_core_tech.chapter3.join_sync;

public class JoinSyncTest {
    public static void main(String[] args) {
        /*
        ThreadB tb = new ThreadB();
        ThreadA ta = new ThreadA(tb);
        ta.start();
        tb.start();
        //main end会先打印，说明join(2000)会先抢到tb锁，然后快速释放
        System.out.println("main end");
        */

        try {
            ThreadB tb = new ThreadB();
            ThreadA ta = new ThreadA(tb);
            ta.start();
            tb.start();
            //join()会和tb线程抢夺锁
            tb.join(2000);
            System.out.println("main end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
