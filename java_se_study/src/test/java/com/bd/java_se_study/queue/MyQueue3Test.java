package com.bd.java_se_study.queue;

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
