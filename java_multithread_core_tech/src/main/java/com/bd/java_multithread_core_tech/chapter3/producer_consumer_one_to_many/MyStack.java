package com.bd.java_multithread_core_tech.chapter3.producer_consumer_one_to_many;

import java.util.ArrayList;
import java.util.List;

public class MyStack {
    private List list = new ArrayList();

    synchronized public void push() {
        String name = Thread.currentThread().getName();

        try {
            //if (list.size() == 1) {
            while (list.size() == 1) {
                System.out.println(name + " push wait");
                this.wait();
            }
            list.add("value = " + Math.random());
            //一个生产者和多个消费者的情况下，需要唤醒所有等待的线程，避免假死
            //this.notify();
            this.notifyAll();
            System.out.println(name + " push = " + list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public String pop() {
        String name = Thread.currentThread().getName();
        String value = "";

        try {
            //一个生产者和多个消费者的情况下，需要循环判断条件，避免消费者唤醒消费者，在pop()中索引越界
            //if (list.size() == 0) {
            while (list.size() == 0) {
                System.out.println(name + " pop wait");
                this.wait();
            }

            value = "" + list.get(0);
            list.remove(0);
            //this.notify();
            this.notifyAll();
            System.out.println(name + " pop = " + list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return value;
    }
}
