package com.bd.java.se.study.queue;

import com.bd.java.se.study.queue.MyQueue1;

public class MyQueue1Test {
    public static void main(String[] args) {
        MyQueue1 queue = new MyQueue1();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        try {
            System.out.println(queue.poll());
            System.out.println(queue.poll());
            System.out.println(queue.poll());
            System.out.println(queue.poll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
