package com.bd.java.multithread.core.tech.chapter2.sync_inner_class;

public class OutClass {
    static class InnerClass {
        public void method1() {
            //持有不同的对象锁，输出结果是无序的
            synchronized ("other lock") {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " i = " + i);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        synchronized public void method2() {
            for (int i = 11; i < 20; i++) {
                System.out.println(Thread.currentThread().getName() + " i = " + i);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
