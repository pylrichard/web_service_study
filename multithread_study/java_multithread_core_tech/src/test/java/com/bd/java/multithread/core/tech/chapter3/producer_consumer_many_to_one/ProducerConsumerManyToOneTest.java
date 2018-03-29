package com.bd.java.multithread.core.tech.chapter3.producer_consumer_many_to_one;

import com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_many.*;

public class ProducerConsumerManyToOneTest {
    public static void main(String[] args) {
        MyStack stack = new MyStack();
        Producer producer1 = new Producer(stack);
        Producer producer2 = new Producer(stack);
        Producer producer3 = new Producer(stack);
        ThreadProducer t1 = new ThreadProducer(producer1);
        t1.setName("producer1");
        ThreadProducer t2 = new ThreadProducer(producer2);
        t2.setName("producer2");
        ThreadProducer t3 = new ThreadProducer(producer3);
        t3.setName("producer3");
        t1.start();
        t2.start();
        t3.start();
        Consumer consumer = new Consumer(stack);
        ThreadConsumer t4 = new ThreadConsumer(consumer);
        t4.setName("consumer");
        t4.start();
    }
}
