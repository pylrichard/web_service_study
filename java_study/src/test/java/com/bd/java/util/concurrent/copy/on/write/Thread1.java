package com.bd.java.util.concurrent.copy.on.write;

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
