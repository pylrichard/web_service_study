package com.bd.sword_for_offer.util;

public class ListNode<T> {
    public T data;
    public ListNode next;

    public ListNode(T data) {
        this.data = data;
        this.next = null;
    }

    public void printList() {
        ListNode node = this;
        while (node != null) {
            System.out.println("node data: " + node.data);
            node = node.next;
        }
    }

    public void printNode() {
        System.out.println("The data in node is: " + this.data);
    }

    public void connectNode(ListNode next) {
        this.next = next;
    }
}
