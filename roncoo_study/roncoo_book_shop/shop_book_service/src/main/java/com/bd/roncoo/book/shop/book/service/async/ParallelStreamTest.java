package com.bd.roncoo.book.shop.book.service.async;

import java.util.function.Function;
import java.util.stream.LongStream;

public class ParallelStreamTest {
    public static void main(String[] args) {
        long n = 20_000_000;
        System.out.println("iterativeSum: " + execute(ParallelStreamTest::iterativeSum, n));
        System.out.println("sequenceSum: " + execute(ParallelStreamTest::sequenceSum, n));
        System.out.println("parallelSum: " + execute(ParallelStreamTest::parallelSum, n));
        System.out.println("wrongSum: " + execute(ParallelStreamTest::wrongSum, n));
    }

    public static long execute(Function<Long, Long> func, long n) {
        long max = 0;
        for (int i = 1; i < 10; i++) {
            long start = System.currentTimeMillis();
            func.apply(n);
            long end = System.currentTimeMillis();
            long cost = end - start;
            if (cost > max) {
                max = cost;
            }
        }

        return max;
    }

    /**
     * 迭代求和
     */
    public static long iterativeSum(long n) {
        long result = 0;
        for (int i = 1; i < n; i++) {
            result += i;
        }

        return result;
    }

    /**
     * 串行求和
     */
    public static long sequenceSum(long n) {
        /*
            iterate()生成装箱对象Long，要拆箱成long进行求和
            iterate()难以拆成独立块并行执行，内部数据结构是链表，不是数组，数组拆分进行并行更高效
            i -> i + 1依赖于上一次的计算结果，所以求和之前数据没有准备好，难以并行化
        */
        return LongStream.rangeClosed(0, n).reduce(0L, Long::sum);
//        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);
    }

    /**
     * 并行求和
     */
    public static long parallelSum(long n) {
        /*
            rangeClosed()生成long
            求和之前数字已经生成完毕
            拆分到与CPU核数相同的多个线程进行并行
        */
        return LongStream.rangeClosed(0, n).parallel().reduce(0L, Long::sum);
//        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
    }

    public static long wrongSum(long n) {
        Counter counter = new Counter();
        LongStream.rangeClosed(0, n).parallel().forEach(counter::add);

        return counter.getTotal();
    }
}
