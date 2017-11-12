package com.bd.java.se.study.util.arrays;

public class ArraysReverseTest {
    public static void main(String[] args) {
        int[] array = {1, 3, 10, 8, 13, 7};

        array = reverseArray1(array);

        printArray(array);
    }

    public static void printArray(int[] array) {
        //JDK 1.5引入加强型循环，不使用下标的情况下遍历数组
        for(int element:array) {
            System.out.println("element: " + element);
        }
    }

    public static int[] reverseArray1(int[] array) {
        int[] revArray = new int[array.length];

        for(int i = 0, j = array.length - 1; i < array.length; i++, j--) {
            revArray[i] = array[j];
        }

        return revArray;
    }
}
