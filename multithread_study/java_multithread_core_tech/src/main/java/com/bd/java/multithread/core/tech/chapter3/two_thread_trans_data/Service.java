package com.bd.java.multithread.core.tech.chapter3.two_thread_trans_data;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private List list = new ArrayList();

    public void add() {
        list.add("pyl");
    }

    public int getSize() {
        return list.size();
    }
}
