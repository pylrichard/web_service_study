package com.bd.java.multithread.core.tech.chapter3.producer_consumer_one_to_one_stack;

import java.util.ArrayList;
import java.util.List;

public class MyStack {
    private List list = new ArrayList();

    synchronized public void push() {
        String name = Thread.currentThread().getName();

        try {
            if (list.size() == 1) {
                System.out.println(name + " push wait");
                this.wait();
            }
            list.add("value = " + Math.random());
            this.notify();
            System.out.println(name + " push = " + list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public String pop() {
        String name = Thread.currentThread().getName();
        String value = "";

        try {
            if (list.size() == 0) {
                System.out.println(name + " pop wait");
                this.wait();
            }

            value = "" + list.get(0);
            list.remove(0);
            this.notify();
            System.out.println(name + " pop = " + list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return value;
    }
}
