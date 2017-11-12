package com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one_stack;

public class Producer {
    private MyStack stack;

    public Producer(MyStack stack) {
        super();
        this.stack = stack;
    }

    public void push() {
        stack.push();
    }
}
