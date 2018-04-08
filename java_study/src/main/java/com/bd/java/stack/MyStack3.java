package com.bd.java.stack;

class Node<T> {
    T data;
    Node pre;

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}

public class MyStack3<T> {
    //head指向栈顶元素
    public Node head;

    public void push(T data) {
        if (head == null) {
            head = new Node(data);
        } else {
            Node node = new Node(data);
            node.pre = head;
            head = node;
        }
    }

    public Node pop() {
        if (head == null) {
            return null;
        }

        Node node = head;
        head = head.pre;

        return node;
    }
}
