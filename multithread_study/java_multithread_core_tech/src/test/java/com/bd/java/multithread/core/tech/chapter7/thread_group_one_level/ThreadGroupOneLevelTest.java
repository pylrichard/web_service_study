package com.bd.java.multithread.core.tech.chapter7.thread_group_one_level;

public class ThreadGroupOneLevelTest {
    public static void main(String[] args) {
        ThreadA ta = new ThreadA();
        ThreadB tb = new ThreadB();
        ThreadGroup group = new ThreadGroup("group");
        Thread t1 = new Thread(group, ta);
        t1.setName("t1");
        Thread t2 = new Thread(group, tb);
        t2.setName("t2");
        t1.start();
        t2.start();
        System.out.println("active count " + group.activeCount());
        System.out.println("group name " + group.getName());
    }
}
