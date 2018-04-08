package com.bd.java.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 双向链表模拟队列
 * 只暴露基于队列的API
 * @param <T> 泛型参数
 */
public class MyQueue2<T> {
    private Queue<T> storage = new LinkedList<>();

    /**
     * 插入元素到队列尾部
     */
    public void offer(T v) {
        storage.offer(v);
    }

    /**
     * 检索但不移除队列头，如果队列为空，返回null
     */
    public T peek() {
        return storage.peek();
    }

    /**
     * 检索但不移除队列头
     * 与peek()的不同是，如果队列为空，抛出1个异常
     */
    public T element() {
        return storage.element();
    }

    /**
     * 检索并移除队列头，如果队列为空，返回null
     */
    public T poll() {
        return storage.poll();
    }

    /**
     * 检索并移除队列头
     * 与poll()的不同是，如果队列为空，抛出1个异常
     */
    public T remove() {
        return storage.remove();
    }

    public boolean empty() {
        return storage.isEmpty();
    }

    /**
     * 打印队列元素
     */
    public String toString() {
        return storage.toString();
    }
}
