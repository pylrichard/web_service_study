package com.bd.java.queue;

import java.util.Stack;

/**
 * 2个栈模拟1个队列
 */
public class MyQueue1 {
    //执行入队操作的栈
    private Stack<Integer> stack1 = new Stack<>();
    //执行出队操作的栈
    private Stack<Integer> stack2 = new Stack<>();

    /**
     * 插入元素到队列尾部
     */
    public void offer(int data) {
        stack1.push(data);
    }

    /**
     * 弹出队列首部的元素
     * @throws Exception 队列为空抛出异常
     */
    public int poll() throws Exception {
        if (stack2.empty()) {
            //注意：stack1中的数据放入stack2之前，先保证stack2是空的
            //要么一开始是空的，要么stack2中元素全部弹出，不然出队的顺序会乱
            //
            //弹出stack1所有元素，放入stack2中
            //最后放入的元素位于stack2栈底
            //最先放入的元素位于stack2栈顶
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }

        if (stack2.empty()) {
            //此时stack2为空有两种可能：
            //1 一开始两个栈都是空的
            //2 stack2所有元素都被弹出
            throw new Exception("MyQueue1 is empty");
        }

        //弹出最先放入的元素
        return stack2.pop();
    }
}
