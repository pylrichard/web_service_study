package com.bd.java.se.study.queue;

import com.bd.java.se.study.queue.MyQueue3;

public class MyQueue3Test {
    public static void main(String[] args) throws Exception {
        MyQueue3 queue = new MyQueue3();
        queue.offer("java");
        queue.offer("scala");
        queue.offer(1);
        System.out.println(queue.poll().getData());
        System.out.println(queue.poll().getData());
        System.out.println(queue.poll().getData());
        System.out.println(queue.poll().getData());
    }
}
