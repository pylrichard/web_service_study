package com.bd.java.util.concurrent.copy.on.write;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListMultiThreadTest {
    public static void main(String[] args) {
        List<Integer> list = new CopyOnWriteArrayList<Integer>();
//        List<Integer> list = new ArrayList<Integer>();
//        List<Integer> list = new LinkedList<Integer>();
        //Vector是相对线程安全，保证增删查改单个操作是原子的，不会被打断，但操作组合起来就不一定是线程安全的
//        List<Integer> list = new Vector<Integer>();

        //CopyOnWriteArrayList中数据量大了，修改代价会很大，但是绝对线程安全
        for (int i = 0; i < 10; i++) {
        //ArrayList/Vector/LinkedList需要有一定数据量，才会触发异常
//        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }

        Thread1 t1 = new Thread1(list);
        Thread2 t2 = new Thread2(list);

        t1.start();
        t2.start();
    }
}
