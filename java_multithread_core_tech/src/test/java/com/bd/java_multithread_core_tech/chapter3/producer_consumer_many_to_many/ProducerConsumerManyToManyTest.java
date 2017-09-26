package com.bd.java_multithread_core_tech.chapter3.producer_consumer_many_to_many;

import com.bd.java_multithread_core_tech.chapter3.producer_consumer_one_to_many.*;

public class ProducerConsumerManyToManyTest {
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

        Consumer consumer1 = new Consumer(stack);
        Consumer consumer2 = new Consumer(stack);
        Consumer consumer3 = new Consumer(stack);
        ThreadConsumer t4 = new ThreadConsumer(consumer1);
        t4.setName("consumer1");
        ThreadConsumer t5 = new ThreadConsumer(consumer2);
        t5.setName("consumer2");
        ThreadConsumer t6 = new ThreadConsumer(consumer3);
        t6.setName("consumer3");
        t4.start();
        t5.start();
        t6.start();
    }
}
