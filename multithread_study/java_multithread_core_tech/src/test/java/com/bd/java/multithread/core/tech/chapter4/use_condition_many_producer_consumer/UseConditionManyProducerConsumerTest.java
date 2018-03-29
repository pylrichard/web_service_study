package com.bd.java.multithread.core.tech.chapter4.use_condition_many_producer_consumer;

public class UseConditionManyProducerConsumerTest {
    private static int THREAD_NUM = 10;

    public static void main(String[] args) {
        MyService service = new MyService();
        ThreadA[] groupA = new ThreadA[THREAD_NUM];
        ThreadB[] groupB = new ThreadB[THREAD_NUM];

        for (int i = 0; i < THREAD_NUM; i++) {
            groupA[i] = new ThreadA(service);
            groupB[i] = new ThreadB(service);
            groupA[i].start();
            groupB[i].start();
        }
    }
}
