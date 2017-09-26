package com.bd.java_multithread_core_tech.chapter3.producer_consumer_one_to_many;

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
