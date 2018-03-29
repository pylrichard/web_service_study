package com.bd.roncoo.multithread.creator.jdk;

import java.util.Arrays;
import java.util.List;

/**
 * Lambda���ʽ
 */
public class Thread7 {
    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(10, 20, 30, 40);
        int result = new Thread7().add(values);
        System.out.println("������Ϊ:" + result);
    }

    public int add(List<Integer> values) {
        //��֤˳��ִ��
        values.forEach(System.out::println);
        //��֤����ִ��
        values.parallelStream().forEach(System.out::println);
        return values.parallelStream().mapToInt(i -> i * 2).sum();
    }
}
