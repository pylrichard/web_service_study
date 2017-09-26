package com.bd.java_multithread_core_tech.chapter3.wait_notify_two_thread_trans_data;

public class ThreadB extends Thread {
    private Object lock;

    public ThreadB(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    Service.add();

                    String name = Thread.currentThread().getName();
                    if (Service.getSize() == 5) {
                        System.out.println(name + " notify begin");
                        //notify()不会立即释放锁
                        lock.notify();
                        System.out.println(name + " notify end");
                    }

                    System.out.println(name + " add " + (i + 1) + " element");
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
