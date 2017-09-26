package com.bd.java_multithread_core_tech.chapter2.sync_one_list;

import java.util.ArrayList;
import java.util.List;

public class OneList {
    //list中只能有1个元素
    private List list = new ArrayList();

    synchronized public void add(String data) {
        list.add(data);
    }

    synchronized public int getSize() {
        return list.size();
    }
}
