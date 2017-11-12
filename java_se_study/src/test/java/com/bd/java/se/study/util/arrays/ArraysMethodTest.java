package com.bd.java.se.study.util.arrays;

import java.util.Arrays;

public class ArraysMethodTest {
    public static void main(String[] args) {
        int[] array = {1, 3, 10, 8, 13, 7};

        Arrays.sort(array);

//        int index = Arrays.binarySearch(array, 13);
//        System.out.println("index " + index);

        //所有元素填充为5
//        Arrays.fill(array, 5);
        printArray(array);
    }

    public static void printArray(int[] array) {
        //JDK 1.5引入加强型循环，不使用下标的情况下遍历数组
        for(int element:array) {
            System.out.println("element: " + element);
        }
    }
}
