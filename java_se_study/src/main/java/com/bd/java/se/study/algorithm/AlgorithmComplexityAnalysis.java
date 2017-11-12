package com.bd.java.se.study.algorithm;

/**
 * 见算法复杂度分析
 */
public class AlgorithmComplexityAnalysis {
    private static int count = 0;

    /**
     * 给定规模n，则基本步骤的执行数量为n，所以时间复杂度为O(n)
     */
    private static int factorial(int n) {
        count++;

        if (n == 0) {
            System.out.println("factorial count " + count);

            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    /**
     * n为数组array的大小，则最坏情况下需要比较n次以得到最大值，所以时间复杂度为O(n)
     */
    private static int findMaxElement(int[] array) {
        int max = array[0];

        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    /**
     * n为数组array的大小，则基本步骤的执行数量约为n*(n-1)/2，所以时间复杂度为O(n2)
     */
    private static long findInversions(int[] array) {
        long inversions = 0;
        int count = 0;

        //外层循环执行n次
        for (int i = 0; i < array.length; i++) {
            //内层循环在每次外层循环下执行n-1、n-2...1、0次
            for (int j = i + 1; j < array.length; j++) {
                count++;
                if (array[i] > array[j]) {
                    inversions++;
                }
            }
        }
        System.out.println("findInversions count " + count);

        return inversions;
    }

    /**
     * 给定规模n和m，则基本步骤的执行数量为n*m，所以时间复杂度为O(n2)
     */
    private static long sumMN(int n, int m) {
        long sum = 0;
        int count = 0;

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                count++;
                sum += x * y;
            }
        }
        System.out.println("sumMN count " + count);

        return sum;
    }

    /**
     * 给定规模n，则基本步骤的执行数量约为n*n*n ，所以时间复杂度为O(n3)
     */
    private static long sum3(int n) {
        long sum = 0;
        int count = 0;

        for (int a = 0; a < n; a++) {
            for (int b = 0; b < n; b++) {
                for (int c = 0; c < n; c++) {
                    count++;
                    sum += a * b * c;
                }
            }
        }
        System.out.println("sum3 count " + count);

        return sum;
    }

    /**
     * 给定规模n，则基本步骤的执行数量为2的n次方，所以时间复杂度为O(2n)
     */
    private static long calculation(int n) {
        long result = 0;
        int count = 0;

        for (int i = 0; i < (1 << n); i++) {
            count++;
            result += i;
        }
        System.out.println("calculation count " + count);

        return result;
    }

    /**
     * 给定规模n，计算fibonacci(n)所需的时间为计算fibonacci(n-1)的时间和计算fibonacci(n-2)的时间的和
     * T(n<=1) = O(1)
     * T(n) = T(n-1) + T(n-2) + O(1)
     * 使用递归树的结构描述可知时间复杂度为O(2n)
     */
    private static int fibonacciRecursion(int n) {
        count++;

        if (n <= 1) {
            return n;
        } else {
            return fibonacciRecursion(n - 1) + fibonacciRecursion(n - 2);
        }
    }

    /**
     * 使用数组f存储计算结果，时间复杂度优化为O(n)
     */
    private static int fibonacciNonRecursionV1(int n) {
        if (n <= 1) {
            return n;
        } else {
            int[] f = new int[n + 1];
            f[0] = 0;
            f[1] = 1;

            for (int i = 2; i <= n; i++) {
                f[i] = f[i - 1] + f[i - 2];
            }

            return f[n];
        }
    }

    /**
     * 实际只有前两个计算结果有用，使用中间变量来存储，就不用创建数组以节省空间。同样时间复杂度优化为O(n)
     */
    private static int fibonacciNonRecursionV2(int n) {
        if (n <= 1) {
            return n;
        } else {
            int iter1 = 0;
            int iter2 = 1;
            int f = 0;

            for (int i = 2; i <= n; i++) {
                f = iter1 + iter2;
                iter1 = iter2;
                iter2 = f;
            }

            return f;
        }
    }

    private static long fibonacciSimple(int n) {
        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2.0;
        double fn = (Math.pow(phi, n) - Math.pow(1 - phi, n)) / sqrt5;

        return (long)fn;
    }

    /**
     * 插入排序的基本操作就是将一个数据插入到已经排好序的有序数据中，从而得到一个新的有序数据。算法适用于少量数据的排序，时间复杂度为O(n2)
     */
    private static void insertionSortInPlace(int[] unsorted) {
        for (int i = 1; i < unsorted.length; i++) {
            if (unsorted[i - 1] > unsorted[i]) {
                int key = unsorted[i];
                int j = i;

                while (j > 0 && unsorted[j - 1] > key) {
                    unsorted[j] = unsorted[j - 1];
                    j--;
                }

                unsorted[j] = key;
            }
        }
    }

    public static void main(String[] args) {
        int n = 6;
        int m = 8;
        int[] array = {6, 2, 5, 4, 3, 1};

        System.out.println("factorial " + factorial(6));
        count = 0;
        System.out.println("findMaxElement " + findMaxElement(array));
        System.out.println("findInversions " + findInversions(array));
        System.out.println("sumMN " + sumMN(n, m));
        System.out.println("sum3 " + sum3(n));
        System.out.println("calculation " + calculation(n));
        System.out.println("fibonacciRecursion " + fibonacciRecursion(n));
        System.out.println("fibonacciRecursion count " + count);
        System.out.println("fibonacciNonRecursionV1 " + fibonacciNonRecursionV1(n));
        System.out.println("fibonacciNonRecursionV2 " + fibonacciNonRecursionV2(n));
        System.out.println("fibonacciSimple " + fibonacciSimple(n));

        insertionSortInPlace(array);
        for (int element:array) {
            System.out.print(element);
        }
    }
}
