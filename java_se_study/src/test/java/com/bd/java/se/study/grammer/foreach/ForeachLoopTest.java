package com.bd.java.se.study.grammer.foreach;

import java.util.ArrayList;
import java.util.List;

//javap -verbose ForeachLoopTest.class观察foreach循环原理
public class ForeachLoopTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("111");
        list.add("222");

        for (String str:list) {
            System.out.println(str);
        }
    }
}
