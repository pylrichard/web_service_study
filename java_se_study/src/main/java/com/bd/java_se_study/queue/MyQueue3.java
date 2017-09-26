package com.bd.java_se_study.queue;

class Node<T> {
    T data;
    Node next;

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}

/**
 * 队列的创建有两种形式：基于数组实现(顺序队列)、基于链表实现(链式队列)
 * 通过链表创建队列，队列扩容比较方便，开销小
 * 出队从头结点开始
 * 入队和链表添加结点一样
 */
public class MyQueue3<T> {
    public Node head;
    public Node tail;

    public void offer(T data) {
        if (head == null) {
            head = new Node(data);
            tail = head;
        } else {
            tail.next = new Node(data);
            tail = tail.next;
        }
    }

    public Node poll() throws Exception {
        if (head == null) {
            throw new Exception("MyQueue3 is empty");
        }

        Node node = head;
        head = head.next;

        return node;
    }
}
