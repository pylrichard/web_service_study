package com.bd.java_multithread_core_tech.chapter2.sync_one_list;

public class Service {
    public OneList add(OneList list, String data) {
        try {
            //线程执行同一个方法的顺序是不确定(异步)的
            //A和B两个线程执行带有条件判断的方法时，可能会出现逻辑错误
            //产生脏读问题
            //解决方法是同步化此处的代码执行
            //list对象是单例，需要对getSize()做同步化调用
            synchronized (list) {
                if (list.getSize() < 1) {
                    //模拟从远程花费2s获取数据
                    Thread.sleep(2000);
                    list.add(data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return list;
    }
}
