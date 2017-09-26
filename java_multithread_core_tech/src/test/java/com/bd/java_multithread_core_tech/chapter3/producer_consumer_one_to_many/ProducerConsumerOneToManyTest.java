package com.bd.java_multithread_core_tech.chapter3.producer_consumer_one_to_many;

public class ProducerConsumerOneToManyTest {
    public static void main(String[] args) {
        MyStack stack = new MyStack();
        Producer producer = new Producer(stack);
        Consumer consumer1 = new Consumer(stack);
        Consumer consumer2 = new Consumer(stack);
        Consumer consumer3 = new Consumer(stack);
        ThreadProducer t1 = new ThreadProducer(producer);
        t1.setName("producer");
        t1.start();
        ThreadConsumer t2 = new ThreadConsumer(consumer1);
        t2.setName("consumer1");
        ThreadConsumer t3 = new ThreadConsumer(consumer2);
        t3.setName("consumer2");
        ThreadConsumer t4 = new ThreadConsumer(consumer3);
        t4.setName("consumer3");
        t2.start();
        t3.start();
        t4.start();
    }
}
