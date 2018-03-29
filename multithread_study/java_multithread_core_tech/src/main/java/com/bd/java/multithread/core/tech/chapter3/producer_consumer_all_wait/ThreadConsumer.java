package com.bd.java.multithread.core.tech.chapter3.producer_consumer_all_wait;

public class ThreadConsumer extends Thread {
    private Consumer consumer;

    public ThreadConsumer(Consumer consumer) {
        super();
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true) {
            consumer.getValue();
        }
    }
}
