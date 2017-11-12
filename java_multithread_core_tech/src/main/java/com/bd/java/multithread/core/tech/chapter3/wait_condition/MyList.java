package com.bd.java.multithread.core.tech.chapter3.wait_condition;

import java.util.ArrayList;
import java.util.List;

public class MyList {
    private static List list = new ArrayList();

    public static int getSize() {
        return list.size();
    }

    public static void add(String str) {
        list.add(str);
    }

    public static void remove(int index) {
        list.remove(index);
    }
}
