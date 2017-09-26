package com.bd.java_se_study.stack;

public class MyStack2Test {
    public static void main(String[] args) {
        MyStack2 stack = new MyStack2();
        stack.push("java");
        stack.push("scala");
        stack.push(3);
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.toString());
    }
}
