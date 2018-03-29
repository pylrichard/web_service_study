package com.bd.java.multithread.core.tech.chapter3.wait_notify_two_thread_trans_data;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private static List list = new ArrayList();

    public static void add() {
        list.add("pyl");
    }

    public static int getSize() {
        return list.size();
    }
}
