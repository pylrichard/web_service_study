package com.bd.java_se_study.stack;

import java.util.LinkedList;

/**
 * 双向链表模拟栈
 * 只暴露基于栈的API
 * @param <T> 泛型参数
 */
public class MyStack2<T> {
    private LinkedList<T> storage = new LinkedList<>();

    public void push(T v) {
        storage.addFirst(v);
    }

    /**
     * 出栈但不删除出栈元素
     */
    public T peek() {
        return storage.getFirst();
    }

    /**
     * 出栈并删除出栈元素
     */
    public T pop() {
        return storage.removeFirst();
    }

    public boolean empty() {
        return storage.isEmpty();
    }

    /**
     * 打印栈元素
     */
    public String toString() {
        return storage.toString();
    }
}
