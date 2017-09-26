package com.bd.java_se_study.util.concurrent.copy_on_write_array_list;

import java.util.List;

public class Thread2 extends Thread {
    private List<Integer> list;

    public Thread2(List<Integer> list) {
        this.list = list;
    }

    public void run() {
        for (int i = 0; i < list.size(); i++) {
            list.remove(i);
        }
    }
}
