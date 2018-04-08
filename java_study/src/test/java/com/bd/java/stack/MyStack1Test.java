package com.bd.java.stack;

import java.util.LinkedList;

public class MyStack1Test {
    public static void main(String[] args) {
        MyStack1 stack = new MyStack1();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.pop();
        System.out.println(stack.top());

        LinkedList<Integer> list = new LinkedList<>();
        list.offer(1);
        list.offer(2);
        list.offer(3);
        System.out.println(list.poll());
    }
}
