package com.bd.java.sort;

import com.bd.java.sort.util.Compare;
import com.bd.java.sort.util.SortUtil;

public class BubbleSort {
    /**
     * 将比较逻辑(变化部分)分离出去，做成接口回调
     */
    Compare compare;

    public void setCompare(Compare compare) {
        this.compare = compare;
    }

    /**
     * 数据正序下一趟扫描即可完成排序。所需的关键字比较次数C和记录移动次数M均达到最小值：
     * Cmin = n-1，Mmin = 0，最好时间复杂度为O(n)
     * 数据反序下需要进行n-1次排序，每次排序需要进行n-i(1<=i<=n-1)次关键字比较
     * 每次比较要移动记录三次交换记录位置(见交换逻辑)
     * 关键字比较次数C和记录移动次数M均达到最大值：
     * Cmax = n*(n-1)/2(0...n-1的累加) = O(n^2)，Mmax = 3*Cmax = O(n^2)，最坏时间复杂度为O(n^2)
     * <p>
     * 平均时间复杂度为O(n^2)
     */
    public void sortV1(SortArray array) {
        int compareCounter = 0;
        int moveCounter = 0;
        boolean swapped = false;
        int dataSize = array.getDataSize();
        //每一次比较都会得出极值
        for (int i = 0; i < dataSize - 1; i++) {
            swapped = false;
            //每一次比较只用到未排序索引位置
            for (int j = 0; j < dataSize - i - 1; j++) {
                compareCounter++;
                long v1 = array.getData(j);
                long v2 = array.getData(j + 1);
                if (compare.compare(v1, v2)) {
                    SortUtil.swap(array, j, j + 1);
                    //设置比较次数，得到时间复杂度
                    moveCounter++;
                    swapped = true;
                }
            }
            //如果比较标志为false，代表数据已经是有序的，不用进行无意义的比较，降低时间复杂度
            if (!swapped) {
                break;
            }
        }
        System.out.println("moveCounter: " + moveCounter + " compareCounter: " + compareCounter);
    }

    /**
     * 关键字比较次数C = (n-2)*(n-1)/2
     * 1...n-2的累加，排除第1个元素i = 0和最后一个元素i = n-1，从第2个元素i = 1开始比较
     * 记录移动次数Mmin = 0，Mmax =
     */
    public void sortV2(SortArray array) {
        //初始认为最左边的值为极值
        int extremeValueIndex = 0;
        int right = array.getDataSize() - 1;
        int i = 1;
        int compareCounter = 0;
        int moveCounter = 0;
        for (; right > 0; right--) {
            //先找出[1, right]区间的极值索引
            for (i = 1, extremeValueIndex = 0; i < right; i++) {
                compareCounter++;
                if (compare.compare(array.getData(i), array.getData(extremeValueIndex))) {
                    extremeValueIndex = i;
                }
            }
            //将极值与最右边的值进行交换
            if (compare.compare(array.getData(extremeValueIndex), array.getData(right))) {
                moveCounter++;
                SortUtil.swap(array, right, extremeValueIndex);
            }
        }
        System.out.println("moveCounter: " + moveCounter + " compareCounter: " + compareCounter);
    }
}

