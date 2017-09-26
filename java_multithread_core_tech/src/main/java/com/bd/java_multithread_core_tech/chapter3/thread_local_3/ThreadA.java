package com.bd.java_multithread_core_tech.chapter3.thread_local_3;

import java.util.Date;

public class ThreadA extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                if (Tools.getTL() == null) {
                    Tools.setTL(new Date());
                }

                System.out.println("ta get value = " + Tools.getTL().getTime());
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
