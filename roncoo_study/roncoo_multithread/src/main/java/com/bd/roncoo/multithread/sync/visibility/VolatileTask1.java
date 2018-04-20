package com.bd.roncoo.multithread.sync.visibility;

public class VolatileTask1 {
    public volatile int value = 1;

    public static void main(String[] args) {
        VolatileTask1 task = new VolatileTask1();
//        task.setValue(10);
        task.value = 10;
//        new Thread(()-> System.out.println(task.getValue())).start();
        new Thread(() -> System.out.println(task.value = 9)).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("执行结果:" + task.getValue());
        System.out.println("执行结果:" + task.value);
    }

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.value = value;
    }
}
