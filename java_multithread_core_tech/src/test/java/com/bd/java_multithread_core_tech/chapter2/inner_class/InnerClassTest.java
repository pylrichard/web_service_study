package com.bd.java_multithread_core_tech.chapter2.inner_class;

public class InnerClassTest {
    public static void main(String[] args) {
        OutClass out = new OutClass();
        out.setName("out");
        out.setPassword("out");
        System.out.println(out.getName() + " " + out.getPassword());

        //实例化内置类，注意使用out.new进行实例化
        //OutClass.InnerClass inner = out.new InnerClass();
        //实例化静态内置类
        OutClass.InnerClass inner = new OutClass.InnerClass();
        inner.setAge("inner");
        inner.setAddress("inner");
        System.out.println(inner.getAge() + " " + inner.getAddress());
    }
}
