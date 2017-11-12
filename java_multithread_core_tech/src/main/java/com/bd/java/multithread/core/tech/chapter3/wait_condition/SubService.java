package com.bd.java.multithread.core.tech.chapter3.wait_condition;

public class SubService {
    private String lock;

    public SubService(String lock) {
        super();
        this.lock = lock;
    }

    public void sub() {
        try {
            synchronized (lock) {
                String name = Thread.currentThread().getName();
                //if (MyList.getSize() == 0) {

                //修改if为while，在AddService添加1个元素后唤醒处于wait状态的t1和t2线程，会再次进行条件判断
                //比如t1线程先获得同步锁之后，进行条件判断，执行remove()，释放同步锁
                //t2线程再获得同步锁，进行条件判断，此时元素个数为0，进入wait状态
                while (MyList.getSize() == 0) {
                    System.out.println(name + " wait begin");
                    lock.wait();
                    System.out.println(name + " wait end");
                }

                //删除操作应该在同步块内，避免不同步发生错误
                MyList.remove(0);
            }

            System.out.println("list size =  " + MyList.getSize());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
