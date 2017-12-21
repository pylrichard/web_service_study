package com.bd.imooc.spring.aop.schema.advice.biz;

public class AspectBiz {
    public void biz() {
        System.out.println("AspectBiz biz.");
        //针对抛出异常后通知
//		throw new RuntimeException();
    }

    public void init(String bizName, int times) {
        System.out.println("AspectBiz init : " + bizName + "   " + times);
    }
}
