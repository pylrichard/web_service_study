package com.bd.java.util.array.list;

import java.util.ArrayList;

public class ArrayListToArrayTest {
    public static void main(String[] args) {
        String[] array = new String[3];
        array[0] = "a";
        array[1] = "b";
        array[2] = "c";
//        array[3] = "d";
//        array[4] = "e";
        System.out.println(array);
        ArrayList<String> list = new ArrayList<String>(4);
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        //当array大小 < list大小时，会分配新的数组存储list的所有元素
        //通过println输出可看出array对象内存地址不同
        //当array大小 >= list大小时，设置array[list.size] = null
        array = list.toArray(array);
        System.out.println(array);
        printArray(array);
    }

    public static void printArray(String[] array) {
        //JDK 1.5引入加强型循环，不使用下标的情况下遍历数组
        for(String element:array) {
            System.out.println("element: " + element);
        }
    }
}
