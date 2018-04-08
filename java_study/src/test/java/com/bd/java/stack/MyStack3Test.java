package com.bd.java.stack;

public class MyStack3Test {
    public static void main(String[] args) {
        MyStack3 stack = new MyStack3();
        stack.push("java");
        stack.push("scala");
        stack.push(1);
        System.out.println(stack.pop().getData());
        System.out.println(stack.pop().getData());
        System.out.println(stack.pop().getData());
    }
}
