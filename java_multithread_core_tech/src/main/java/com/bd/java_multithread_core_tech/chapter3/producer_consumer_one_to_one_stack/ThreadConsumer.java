package com.bd.java_multithread_core_tech.chapter3.producer_consumer_one_to_one_stack;

public class ThreadConsumer extends Thread {
    private Consumer consumer;

    public ThreadConsumer(Consumer consumer) {
        super();
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true) {
            consumer.pop();
        }
    }
}
