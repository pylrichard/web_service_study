package com.bd.geek.design.pattern.singleton;

public class ChocolateFactory {
    private boolean empty;
    private boolean boiled;
    /**
     * 2 立即创建对象
     * 构造函数返回对象
     */
//    public static ChocolateFactory uniqueInstance = new ChocolateFactory();
    /**
     * volatile从主内存读取变量到线程工作内存
     */
    public volatile static ChocolateFactory uniqueInstance = null;

    /**
     * 构造函数设为私有方法
     */
    private ChocolateFactory() {
        empty = true;
        boiled = false;
    }

    /**
     * 1 方法添加synchronized关键字
     */
    public static ChocolateFactory getInstance() {
        if (uniqueInstance == null) {
            /*
                3 使用双重检验进行多线程同步
             */
            synchronized (ChocolateFactory.class) {
                /*
                    避免第二个线程获得锁后再次创建对象
                 */
                if (uniqueInstance == null) {
                    uniqueInstance = new ChocolateFactory();
                }
            }
        }

        return uniqueInstance;
    }

    public void fill() {
        if (empty) {
            empty = false;
            boiled = false;
        }
    }

    public void drain() {
        if ((!empty) && boiled) {
            empty = true;
        }
    }

    public void boil() {
        if ((!empty) && (!boiled)) {
            boiled = true;
        }
    }
}
