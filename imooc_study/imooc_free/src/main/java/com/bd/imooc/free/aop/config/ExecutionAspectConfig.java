package com.bd.imooc.free.aop.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 匹配任何公共方法
 * 注解@Pointcut("execution(public * com.bd.imooc.service.*.*(..))")
 *
 * 匹配com.bd.imooc包及子包下Service类中无参方法
 * 注解@Pointcut("execution(* com.bd.imooc..*Service.*())")
 *
 * 匹配com.bd.imooc包及子包下Service类中的任何只有一个参数的方法
 * 注解@Pointcut("execution(* com.bd.imooc..*Service.*(*))")
 *
 * 匹配com.bd.imooc包及子包下任何类的任何方法
 * 注解@Pointcut("execution(* com.bd.imooc..*.*(..))")
 *
 * 匹配com.bd.imooc包及子包下返回值为String的任何方法
 * 注解@Pointcut("execution(String com.bd.imooc..*.*(..))")
 *
 * 匹配异常
 * 注解execution(public * com.bd.imooc.service.*.*(..) throws java.lang.IllegalAccessException)
 */
@Aspect
@Component
public class ExecutionAspectConfig {
    @Pointcut("execution(public * com.bd.imooc.aop.service..*Service.*(..) throws java.lang.IllegalAccessException)")
    public void matchCondition() {}

    @Before("matchCondition()")
    public void before() {
        System.out.println("ExecutionAspectConfig");
        System.out.println("###before");
    }
}

