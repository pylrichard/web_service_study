package com.bd.java.multithread.core.tech.chapter3.thread_local_3;

import java.util.Date;

public class ThreadB extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                if (Tools.getTL() == null) {
                    Tools.setTL(new Date());
                }

                System.out.println("tb get value = " + Tools.getTL().getTime());
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
