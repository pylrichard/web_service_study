package com.bd.java.stack;

import java.util.LinkedList;

/**
 * 2个队列实现1个栈
 * 任意时刻2个队列总有1个为空
 */
public class MyStack1 {
    //使用双向链表模拟队列
    LinkedList<Integer> queue1 = new LinkedList<>();
    LinkedList<Integer> queue2 = new LinkedList<>();

    public void push(int x) {
        //优先放入为空的队列中
        if (!queue2.isEmpty()) {
            queue2.offer(x);
        } else {
            queue1.offer(x);
        }
    }

    public void pop() {
        //2个队列都不为空时才有元素弹出
        if (!empty()) {
            //如果queue1为空，queue2有元素， 将queue2的所有元素依次放入queue1中，弹出最后放入的元素
            if (queue1.isEmpty()) {
                while (queue2.size() > 1) {
                    //poll()弹出首元素，即先放入的元素
                    //offer()将元素放入尾部
                    queue1.offer(queue2.poll());
                }

                queue2.poll();
            } else {
                while (queue1.size() > 1) {
                    queue2.offer(queue1.poll());
                }

                queue1.poll();
            }
        }
    }

    /**
     * @return 返回顶端元素，即最后放入的元素
     */
    public int top() {
        if (queue1.isEmpty()) {
            while (queue2.size() > 1) {
                queue1.offer(queue2.poll());
            }

            int x = queue2.poll();
            queue1.offer(x);

            return x;
        } else {
            while (queue1.size() > 1) {
                queue2.offer(queue1.poll());
            }

            int x = queue1.poll();
            queue2.offer(x);

            return x;
        }
    }

    public boolean empty() {
        return queue1.isEmpty() && queue2.isEmpty();
    }
}
