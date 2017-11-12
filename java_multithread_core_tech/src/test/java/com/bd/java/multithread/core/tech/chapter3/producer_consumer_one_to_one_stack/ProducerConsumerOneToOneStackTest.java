package com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one_stack;

import com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one_stack.*;

public class ProducerConsumerOneToOneStackTest {
    public static void main(String[] args) {
        MyStack stack = new MyStack();
        Producer producer = new Producer(stack);
        Consumer consumer = new Consumer(stack);
        ThreadProducer t1 = new ThreadProducer(producer);
        ThreadConsumer t2 = new ThreadConsumer(consumer);
        t1.start();
        t2.start();
    }
}
