package com.bd.sword_for_offer.reverse_list;

import com.bd.sword_for_offer.util.ListNode;

/**
 * 《剑指Offer》的面试题16-反转链表
 */ 
public class ReverseList {
    /**
     * 反转链表，循环方式实现
     * @param head 链表头节点
     * @return 反转后的链表头节点(原链表的尾节点)
     */
    public ListNode reverseWithLoop(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode reverseHead = null;
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode next = current.next;

            if (next == null) {
                reverseHead = current;
            }

            //进行链表反转，发生断链
            current.next = prev;
            //解决断链，prev保存当前节点
            prev = current;
            //下一个节点作为当前节点
            current = next;
        }

        return reverseHead;
    }

    /**
     * 反转链表，递归方式实现
     */
    public ListNode reverseWithRecursion(ListNode head) {
        //递归到链表尾节点，就跳出递归
        if (head == null || head.next == null) {
            return head;
        } else {
            //newHead是链表尾节点
            ListNode newHead = reverseWithRecursion(head.next);
            //从链表尾节点开始进行反转
            head.next.next = head;
            head.next = null;

            return newHead;
        }
    }


    /**
     * 链表有多个节点
     */
    public static void testCase1(ReverseList list) {
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);

        head.connectNode(node2);
        node2.connectNode(node3);

        System.out.println("The original list is: ");
        head.printList();

//        ListNode reverseHead = list.reverseWithLoop(head);
        ListNode reverseHead = list.reverseWithRecursion(head);

        System.out.println("The reversed list is: ");
        reverseHead.printList();
    }

    //链表只有1个节点
    public static void testCase2(ReverseList list) {
        ListNode head = new ListNode(1);

        System.out.println("The original list is: ");
        head.printList();

//        ListNode reverseHead = list.reverseWithLoop(head);
        ListNode reverseHead = list.reverseWithRecursion(head);

        System.out.println("The reversed list is: ");
        reverseHead.printList();
    }

    //空链表
    public static void testCase3(ReverseList list) {
//        list.reverseWithLoop(null);
        list.reverseWithRecursion(null);
    }

    public static void main(String[] args) {
        ReverseList list = new ReverseList();
        testCase1(list);
        testCase2(list);
        testCase3(list);
    }
}
