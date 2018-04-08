package com.bd.java.sort.util;

import com.bd.java.sort.SortArray;

public class SortUtil {
    public static void swap(SortArray array, int index1, int index2) {
        long temp = array.getData(index1);
        array.setData(index1, array.getData(index2));
        array.setData(index2, temp);
    }
}
