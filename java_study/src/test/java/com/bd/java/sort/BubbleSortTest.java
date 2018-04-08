package com.bd.java.sort;

import com.bd.java.sort.util.CompareDesc;

public class BubbleSortTest {
    public static void main(String[] args) {
        SortArray array = new SortArray();

//        //数据乱序
        array.insert(30);
        array.insert(78);
        array.insert(56);
        array.insert(102);
        array.insert(1);
        array.insert(23);
        array.insert(789);

//        //数据正序
//        array.insert(1);
//        array.insert(23);
//        array.insert(30);
//        array.insert(56);
//        array.insert(78);
//        array.insert(102);
//        array.insert(789);

        //数据反序
//        array.insert(789);
//        array.insert(102);
//        array.insert(78);
//        array.insert(56);
//        array.insert(30);
//        array.insert(23);
//        array.insert(1);

//        CompareAsc compare = new CompareAsc();
        CompareDesc compare = new CompareDesc();
        BubbleSort sort = new BubbleSort();
        sort.setCompare(compare);
        array.printArray();
        sort.sortV2(array);
        array.printArray();
    }
}
