package com.bd.java_multithread_core_tech.chapter2.sync_update_new_value;

public class Service {
    private boolean isRunning = true;
    private Object object = new Object();

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void run() {
        while (isRunning() == true) {
            //没有synchronized，A和B线程获取到的isRunning值是不一样的，A线程进入死循环
            //添加synchronized关键字后可以不需要volatile关键字，synchronized可以保证数据可见性
            //synchronized不能放在while循环之外，A线程获得同步对象锁之后进入死循环
            //没有机会切换到B线程，调用stop()
            synchronized (object) {}
        }
        System.out.println("service stoped");
    }

    public void stop() {
        setRunning(false);
    }
}
