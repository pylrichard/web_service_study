package com.bd.java.multithread.core.tech.chapter4.use_condition_thread_run_in_order;

public class UseConditionThreadRunInOrderTest {
    private static int THREAD_NUM = 5;

    public static void main(String[] args) {
        MyService service = new MyService();
        Thread[] groupA = new Thread[THREAD_NUM];
        Thread[] groupB = new Thread[THREAD_NUM];
        Thread[] groupC = new Thread[THREAD_NUM];

        for (int i = 0; i < THREAD_NUM; i++) {
            groupA[i] = new ThreadA(service);
            groupA[i].setName("A" + i);
            groupB[i] = new ThreadB(service);
            groupB[i].setName("B" + i);
            groupC[i] = new ThreadC(service);
            groupC[i].setName("C" + i);

            groupA[i].start();
            groupB[i].start();
            groupC[i].start();
        }
    }
}
