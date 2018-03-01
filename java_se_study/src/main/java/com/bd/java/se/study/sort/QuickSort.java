package com.bd.java.se.study.sort;

import com.bd.java.se.study.sort.util.CompareDesc;
import com.bd.java.se.study.sort.util.SortUtil;

public class QuickSort {
    private static final int ARRAY_AVAILABLE_LENGTH = 7;
    private long pivotValue;

    public void sort(SortArray array) {
        sortImpl(array, 0, array.getDataSize() - 1);
    }

    private void sortImpl(SortArray array, int low, int high) {
        int pivot;
        //快速排序对于大数组，见大话数据结构/9.9.3-6.png
        if (array.getDataSize() > ARRAY_AVAILABLE_LENGTH) {
            //优化递归操作，见大话数据结构/9.9.3-7.png
            while (low < high) {
                pivot = partition(array, low, high);
                sortImpl(array, low, pivot - 1);
                low = pivot + 1;
            }
        } else {
            CompareDesc compare = new CompareDesc();
            BubbleSort sort = new BubbleSort();
            sort.setCompare(compare);
            sort.sortV2(array);
        }
    }

    /**
     * 将数组分区为2部分，左边部分小于枢轴的值，右边部分大于枢轴的值
     *
     * @return 枢轴的索引
     */
    private int partition(SortArray array, int low, int high) {
        int mid = low + (high - low) >> 1;
        //优化选取枢轴，见大话数据结构/9.9.3-1~3.png
        if (array.getData(low) > array.getData(high)) {
            SortUtil.swap(array, low, high);
        }
        if (array.getData(mid) > array.getData(high)) {
            SortUtil.swap(array, mid, high);
        }
        if (array.getData(mid) > array.getData(low)) {
            SortUtil.swap(array, mid, low);
        }
        //保存枢轴的值
        this.pivotValue = array.getData(low);
        long pivotValue = array.getData(low);
        while (low < high) {
            while (low < high && array.getData(high) >= pivotValue) {
                high--;
            }
            //优化不必要的交换，见大话数据结构/9.9.3-4~5.png
            array.setData(low, array.getData(high));
            while (low < high && array.getData(low) <= pivotValue) {
                low++;
            }
            array.setData(high, array.getData(low));
        }
        array.setData(low, this.pivotValue);

        return low;
    }
}
