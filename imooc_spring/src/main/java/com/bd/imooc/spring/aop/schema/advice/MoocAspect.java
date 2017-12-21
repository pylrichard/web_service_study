package com.bd.imooc.spring.aop.schema.advice;

import org.aspectj.lang.ProceedingJoinPoint;

public class MoocAspect {
    public void before() {
        System.out.println("MoocAspect before.");
    }

    public void afterReturning() {
        System.out.println("MoocAspect afterReturning.");
    }

    public void afterThrowing() {
        System.out.println("MoocAspect afterThrowing.");
    }

    public void after() {
        System.out.println("MoocAspect after.");
    }

    /**
     * 注意捕捉异常的时机
     */
    public Object around(ProceedingJoinPoint pjp) {
        Object obj = null;
        try {
            System.out.println("MoocAspect around 1.");
            //实际方法调用
            obj = pjp.proceed();
            System.out.println("MoocAspect around 2.");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return obj;
    }

    public Object aroundInit(ProceedingJoinPoint pjp, String bizName, int times) {
        System.out.println(bizName + "   " + times);
        Object obj = null;
        try {
            System.out.println("MoocAspect aroundInit 1.");
            obj = pjp.proceed();
            System.out.println("MoocAspect aroundInit 2.");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return obj;
    }
}
