package com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one;

public class ProducerConsumerOneToOneTest {
    public static void main(String[] args) {
        String lock = new String("");
        Producer producer = new Producer(lock);
        Consumer consumer = new Consumer(lock);
        ThreadProducer t1 = new ThreadProducer(producer);
        ThreadConsumer t2 = new ThreadConsumer(consumer);
        t1.start();
        t2.start();
    }
}
