package com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_many;

public class Consumer {
    private MyStack stack;

    public Consumer(MyStack stack) {
        super();
        this.stack = stack;
    }

    public void pop() {
        stack.pop();
    }
}
