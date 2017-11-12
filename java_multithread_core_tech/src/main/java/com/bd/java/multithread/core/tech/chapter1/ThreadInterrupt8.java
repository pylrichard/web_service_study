package com.bd.java.multithread.core.tech.chapter1;

public class ThreadInterrupt8 extends Thread {
    @Override
    public void run() {
        while(true) {
            if(this.isInterrupted()) {
                System.out.println("interrupted");
                //建议使用抛异常的方法实现线程停止，在catch中可以将异常向上一层抛出，广播线程停止事件
                return;
            }

            System.out.println("time " + System.currentTimeMillis());
        }
    }
}
