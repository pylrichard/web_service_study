package com.bd.java_se_study.util.hash_set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/*
    "不保证有序"和"保证无序"不等价，HashSet的iterator是前者而不是后者，所以在一次运行中看到有序的结果也是正常的，但不能依赖这个有序行为
    HashSet并不关心key的排序，就算其iterator有序通常也是按元素插入顺序，LinkedHashSet就支持插入顺序遍历
    在此的输出结果有序纯粹是个巧合
    JDK8的HashSet实现变了，导致元素插入的位置发生了变化
    iterator自身实现的顺序倒没变，还是按照内部插入的位置顺序来遍历
    JDK7和JDK8的结果不一样
    具体来说是JDK7与JDK8的java.util.HashMap的hash算法以及HashMap的数据布局发生了变化
    插入HashSet的是Integer，其hashCode()返回int值本身
    所以在对象hashCode这一步引入了巧合的按大小排序
    然后HashMap.hash(Object)获取了对象的hashCode()之后会尝试进一步混淆
    JDK8的java.util.HashMap内的hash算法比JDK7的混淆程度低
    在[0, 2^32-1]范围内经过HashMap.hash()之后还是得到自己，以下例子正好落入这个范围内
    外加load factor正好在此例中让这个HashMap没有hash冲突，导致例中元素正好按大小顺序插入在HashMap的开放式哈希表里
    要产生无序的结果，把插入的数字先加上2的16次方，然后拿出来之后再减去2的16次方
 */
public class HashSetOrderTest {
    public static void main(String[] args){
        //40设置产生随机数的seed
        Random rand = new Random(40);
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < 1000; i++) {
            //Set中不能存储重复的元素，可以存储null。如果需要存储多个重复元素，需要使用List
            //30设置随机数大小范围
            set.add(rand.nextInt(30));
//            set.add(rand.nextInt(30) + (1 << 16));
        }

        Iterator<Integer> iterator = set.iterator();
        while(iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
//            System.out.print((iterator.next() - (1 << 16)) + " ");
        }
    }
}
