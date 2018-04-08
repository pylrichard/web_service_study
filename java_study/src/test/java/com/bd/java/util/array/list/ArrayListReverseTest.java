package com.bd.java.util.array.list;

import java.util.ArrayList;
import java.util.Collections;

public class ArrayListReverseTest {
    public static void main(String[] args) {
        //reverseArray1();
        reverseArray2();
    }

    public static void reverseArray1() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        Collections.reverse(list);
        System.out.println(list);
    }

    public static void reverseArray2() {
        String[] array = {"A", "B", "C"};
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < array.length; i++) {
            list.add(array[array.length - i - 1]);
        }

        array = list.toArray(array);
        printArray(array);
    }

    public static void printArray(String[] array) {
        //JDK 1.5引入加强型循环，不使用下标的情况下遍历数组
        for(String element:array) {
            System.out.println("element: " + element);
        }
    }
}
