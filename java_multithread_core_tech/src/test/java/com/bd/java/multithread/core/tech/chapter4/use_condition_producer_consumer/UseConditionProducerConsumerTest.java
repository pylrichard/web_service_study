package com.bd.java.multithread.core.tech.chapter4.use_condition_producer_consumer;

import com.bd.java.multithread.core.tech.chapter4.use_condition_producer_consumer.MyService;
import com.bd.java.multithread.core.tech.chapter4.use_condition_producer_consumer.ThreadA;
import com.bd.java.multithread.core.tech.chapter4.use_condition_producer_consumer.ThreadB;

public class UseConditionProducerConsumerTest {
    public static void main(String[] args) {
        MyService service = new MyService();
        ThreadA t1 = new ThreadA(service);
        t1.start();
        ThreadB t2 = new ThreadB(service);
        t2.start();
    }
}
