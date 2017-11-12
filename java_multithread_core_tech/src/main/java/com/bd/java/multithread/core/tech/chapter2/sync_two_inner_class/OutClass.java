package com.bd.java.multithread.core.tech.chapter2.sync_two_inner_class;

public class OutClass {
    static class InnerClass1 {
        public void method1(InnerClass2 class2) {
            String name = Thread.currentThread().getName();

            //对class2上锁后，其它线程只能以同步方式调用class2的静态同步方法
            synchronized (class2) {
                System.out.println(name + " method1 begin");

                for (int i = 0; i < 10; i++) {
                    System.out.println("i = " + i);

                    try {
                        //注意此处睡眠时间要一致
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(name + " method1 end");
            }
        }

        synchronized public void method2() {
            String name = Thread.currentThread().getName();

            System.out.println(name + " method2 begin");

            for (int j = 0; j < 10; j++) {
                System.out.println("j = " + j);

                try {
                    //注意此处睡眠时间要一致
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(name + " method2 end");
        }
    }

    static class InnerClass2 {
        synchronized public void method1() {
            String name = Thread.currentThread().getName();

            System.out.println(name + " method1 begin");

            for (int k = 0; k < 10; k++) {
                System.out.println("k = " + k);

                try {
                    //注意此处睡眠时间要一致
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(name + " method1 end");
        }
    }
}
