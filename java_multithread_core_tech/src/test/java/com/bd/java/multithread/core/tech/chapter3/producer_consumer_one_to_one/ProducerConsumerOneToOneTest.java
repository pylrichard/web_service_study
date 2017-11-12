package com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one;

import com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one.Consumer;
import com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one.Producer;
import com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one.ThreadConsumer;
import com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one.ThreadProducer;

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
