package com.bd.java.multithread.core.tech.chapter2.atomic_long_not_safe;

public class AtomicLongNotSafeTest {
    //根据不同的CPU执行指令的快慢，需要增大数量才能看出效果
    private static int THREAD_NUM = 20;

    public static void main(String[] args) {
        try {
            Service service = new Service();
            MyThread[] group = new MyThread[THREAD_NUM];

            for (int i = 0; i < THREAD_NUM; i++) {
                group[i] = new MyThread(service);
            }

            for (int i = 0; i < THREAD_NUM; i++) {
                group[i].start();
            }

            Thread.sleep(1000);
            //打印顺序是乱序的，因为addAndGet()是原子操作，但线程对Service.addCount()的调用不是原子的，需要进行同步
            System.out.println(service.getCount().get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
