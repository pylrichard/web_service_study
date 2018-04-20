package com.bd.roncoo.multithread.sync.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicTask {
    private AtomicInteger atomicInteger = new AtomicInteger();
    private int[] intArray = {1, 2, 3, 4};
    private User user = new User();
    private AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(intArray);
    private AtomicReference<User> atomicReference = new AtomicReference<>();
    private AtomicIntegerFieldUpdater<User> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    public static void main(String[] args) {
        AtomicTask task = new AtomicTask();
        new Thread(task::run).start();
        new Thread(task::run).start();
        new Thread(task::run).start();
    }

    public void run() {
        System.out.println(Thread.currentThread().getName());
        updateAtomicArray();
        updateAtomicField();
        incAtomicInteger();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateAtomicArray() {
        atomicIntegerArray.getAndIncrement(2);
        atomicIntegerArray.getAndAdd(2, 10);
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.print(atomicIntegerArray.get(i) + " ");
        }
        System.out.println();
    }

    private void updateAtomicField() {
        System.out.println("age " + fieldUpdater.getAndIncrement(user));
        System.out.println("age " + fieldUpdater.getAndIncrement(user));
        System.out.println("age " + fieldUpdater.getAndIncrement(user));
    }

    private void incAtomicInteger() {
        System.out.println("integer " + atomicInteger.getAndIncrement());
    }
}
