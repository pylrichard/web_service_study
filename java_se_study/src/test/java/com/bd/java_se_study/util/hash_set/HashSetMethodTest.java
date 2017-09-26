package com.bd.java_se_study.util.hash_set;

import java.util.HashSet;
import java.util.Set;

/*
    HastSet散列表的大小默认为16，加载因子为0.75
    去重原理：当HastSet add一个元素A时，首先通过hashCode()获取这个元素的hash值，假设hash值为400，然后将hash值对散列表大小取模，400%16 = 0
    0表示第一个链表，然后通过equals()将元素A与散列表中的第一个链表的每个元素进行比较
    如果该链表中没有找到与元素A相同的元素，则将元素A添加到该链表
    如果找到某个元素与元素A相同，表示Set中已经存在了该元素，不添加元素A

    容量扩容原理：当散列表中为非空链表数量除以散列表大小>加载因子时，HashSet会进行再散列
    即将散列表大小在原有基础上x2，对所有元素进行重新散列，得到新的散列表
    假设现在HashSet散列表大小为8，加载因子为0.75，元素有30个，第一个链表包含14个元素，第二个链表为空，以此类推分别为：
    14 0 0 4 2 2 2 6
    添加第31个元素B，B的hash值为9，9%8 = 1，所以将元素B与第二个链表中的元素进行去重比较
    发现第二个链表为空，所以将元素B添加到第二个链表
    此时散列表各个链表的元素个数分别为14 1 0 4 2 2 2 6
    非空链表数量除以散列表的大小为7/8>0.75，这时会进行再散列，散列表的大小为8x2 = 16。当元素不断增加时，以此类推扩容
 */
public class HashSetMethodTest {
    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();

        set1.add(10);
        set1.add(11);
        set2.add(20);
        set2.add(21);
        set1.addAll(set2);
//        set1.remove(20);
//        set1.removeAll(set2);
//        set1.clear();

        printSet(set1);
    }

    public static void printSet(Set<Integer> set) {
        for (Integer element:set) {
            System.out.println(element);
        }
    }
}
