package com.bd.java.search;

import org.junit.Assert;

import java.util.Arrays;

public class BinarySearch {
    public static int search(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) >> 1;
            int value = array[mid];

            if (value < key) {
                low = mid + 1;
            } else if (value > key) {
                high = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] array = {9, 6, 7, 1, 3, 2, 4, 5};

        Arrays.sort(array);
        Assert.assertEquals(search(array, 6), 5);
        Assert.assertEquals(search(array, 8), -1);
    }
}
