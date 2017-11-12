package com.bd.java.multithread.core.tech.chapter7.thread_group_recurse_get;

public class ThreadGroupRecurseGetTest {
    public static void main(String[] args) {
        ThreadGroup main = Thread.currentThread().getThreadGroup();
        ThreadGroup sub1 = new ThreadGroup(main, "sub1");
        ThreadGroup sub2 = new ThreadGroup(sub1, "sub2");
        ThreadGroup[] groups = new ThreadGroup[main.activeGroupCount()];
        //true表示递归获取所有子线程组
        main.enumerate(groups, true);
        for (int i = 0; i < groups.length; i++) {
            System.out.println(groups[i].getName());
        }
    }
}
