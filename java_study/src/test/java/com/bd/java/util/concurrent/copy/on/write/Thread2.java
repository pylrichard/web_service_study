package com.bd.java.util.concurrent.copy.on.write;

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
