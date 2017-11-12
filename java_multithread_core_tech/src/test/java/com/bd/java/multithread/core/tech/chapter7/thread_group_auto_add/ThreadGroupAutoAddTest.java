package com.bd.java.multithread.core.tech.chapter7.thread_group_auto_add;

public class ThreadGroupAutoAddTest {
    public static void main(String[] args) {
        String name = Thread.currentThread().getName();
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        //JVM的根线程组是system
        System.out.println("root group name " + group.getParent().getName());
        System.out.println(name + " belong to group " + group.getName() + " thread num " + group.activeGroupCount());

        //添加到main线程所在的线程组
        ThreadGroup newGroup = new ThreadGroup("newGroup");
        name = Thread.currentThread().getName();
        group = Thread.currentThread().getThreadGroup();
        int count = group.activeGroupCount();
        System.out.println(name + " belong to group " + group.getName() + " thread num " + group.activeGroupCount());

        ThreadGroup[] groups = new ThreadGroup[count];
        group.enumerate(groups);
        for (int i = 0; i < groups.length; i++) {
            System.out.println("group name " + groups[i].getName());
        }
    }
}
