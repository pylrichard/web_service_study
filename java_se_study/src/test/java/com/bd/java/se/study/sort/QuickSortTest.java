package com.bd.java.se.study.sort;

import com.bd.java.se.study.sort.QuickSort;
import com.bd.java.se.study.sort.SortArray;

public class QuickSortTest {
    public static void main(String[] args) {
        SortArray array = new SortArray();

//        //数据乱序
//        array.insert(50);
//        array.insert(10);
//        array.insert(90);
//        array.insert(30);
//        array.insert(70);
//        array.insert(40);
//        array.insert(80);
//        array.insert(60);
//        array.insert(20);

//        //数据正序
//        array.insert(10);
//        array.insert(20);
//        array.insert(30);
//        array.insert(40);
//        array.insert(50);
//        array.insert(60);
//        array.insert(70);
//        array.insert(80);
//        array.insert(90);

        //数据反序
        array.insert(90);
        array.insert(80);
        array.insert(70);
        array.insert(60);
        array.insert(50);
        array.insert(40);
        array.insert(30);
        array.insert(20);
        array.insert(10);

        QuickSort sort = new QuickSort();
        array.printArray();
        sort.sort(array);
        array.printArray();
    }
}
