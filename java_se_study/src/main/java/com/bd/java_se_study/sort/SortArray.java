package com.bd.java_se_study.sort;

public class SortArray {
    private long array[];
    private int dataSize = 0;
    private int DEFAULT_SIZE = 20;

    public SortArray() {
        array = new long[DEFAULT_SIZE];
    }

    public SortArray(int size) {
        array = new long[size];
    }

    public int getDataSize() {
        return dataSize;
    }

    public long getData(int index) {
        if (index >= 0 && index < dataSize) {
            return array[index];
        } else {
            return -1;
        }
    }

    public void setData(int index, long value) {
        if (index >= 0 && index < dataSize) {
            array[index] = value;
        }
    }

    public void insert(long value) {
        if (dataSize < array.length - 1) {
            array[dataSize] = value;
            dataSize++;
        } else {
            System.out.println("array is full, insert is failed");
        }
    }

    public void printArray() {
        for(int i = 0; i < dataSize; i++) {
            long value = array[i];
            System.out.print(value + " ");
        }

        System.out.println();
    }
}
