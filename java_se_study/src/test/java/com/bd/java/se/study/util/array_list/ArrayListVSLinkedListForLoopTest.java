package com.bd.java.se.study.util.array_list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//见图解结合LinkedList
public class ArrayListVSLinkedListForLoopTest {
    private static int SIZE = 10000;

    private static void loopList(List<Integer> list)
    {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++)
        {
            //ArrayList执行get()，时间复杂度为O(1)
            //LinkedList执行get()，时间复杂度为O(n)
            list.get(i);
        }
        System.out.println(list.getClass().getSimpleName() + " for loop time " +
                (System.currentTimeMillis() - startTime) + "ms");

        startTime = System.currentTimeMillis();
        for (Integer i : list) {}
        System.out.println(list.getClass().getSimpleName() + " foreach loop time " +
                (System.currentTimeMillis() - startTime) + "ms");
    }

    public static void main(String[] args)
    {
        List<Integer> arrayList = new ArrayList<Integer>(SIZE);
        List<Integer> linkedList = new LinkedList<Integer>();

        for (int i = 0; i < SIZE; i++)
        {
            arrayList.add(i);
            linkedList.add(i);
        }

        loopList(arrayList);
        loopList(linkedList);
        System.out.println();
    }
}
