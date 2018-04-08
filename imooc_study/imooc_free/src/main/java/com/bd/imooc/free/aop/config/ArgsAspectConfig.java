package com.bd.imooc.free.aop.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 匹配任何以find开头而且只有一个Long参数的方法
 * 注解@Pointcut("execution(* *..find*(Long))")
 *
 * 匹配任何以find开头的而且第一个参数为Long型的方法
 * 注解@Pointcut("execution(* *..find*(Long,..))")
 *
 * 匹配任何只有一个Long参数的方法
 * 注解@Pointcut("within(com.bd.imooc..*) && args(Long)")
 *
 * 匹配第一个参数为Long型的方法
 * 注解@Pointcut("within(com.bd.imooc..*) && args(Long,..)")
 */
@Aspect
@Component
public class ArgsAspectConfig {
    @Pointcut("args(Long) && within(com.bd.imooc.aop.service.*)")
    public void matchArgs(){}

    @Before("matchArgs()")
    public void before(){
        System.out.println("ArgsAspectConfig");
        System.out.println("###before");
    }
}
