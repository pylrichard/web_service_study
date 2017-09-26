package com.bd.java_se_study.util.concurrent.copy_on_write_array_list;

import java.util.List;

public class Thread1 extends Thread {
    private List<Integer> list;

    public Thread1(List<Integer> list) {
        this.list = list;
    }

    public void run() {
        for (Integer i:list) {}
    }
}
