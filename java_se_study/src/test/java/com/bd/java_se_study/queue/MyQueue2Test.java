package com.bd.java_se_study.queue;

public class MyQueue2Test {
    public static void main(String[] args) {
        MyQueue2 queue = new MyQueue2();
        queue.offer("java");
        queue.offer("scala");
        queue.offer(1);
        System.out.println(queue.peek());
        System.out.println(queue.element());
        System.out.println(queue.poll());
        System.out.println(queue.remove());
    }
}
