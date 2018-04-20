package com.bd.roncoo.multithread.creator.jdk;

import java.util.Arrays;
import java.util.List;

/**
 * Lambda创建方式
 */
public class LambdaThread {
    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(10, 20, 30, 40);
        int result = new LambdaThread().add(values);
        System.out.println("执行结果:" + result);
    }

    public int add(List<Integer> values) {
        //验证串行执行
        values.forEach(System.out::println);
        //验证并行执行
        values.parallelStream().forEach(System.out::println);
        return values.parallelStream().mapToInt(i -> i * 2).sum();
    }
}
