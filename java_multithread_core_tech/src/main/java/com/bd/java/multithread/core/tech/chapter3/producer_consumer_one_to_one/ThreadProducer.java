package com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one;

public class ThreadProducer extends Thread {
    private Producer producer;

    public ThreadProducer(Producer producer) {
        super();
        this.producer = producer;
    }

    @Override
    public void run() {
        while (true) {
            producer.setValue();
        }
    }
}
