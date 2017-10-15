package com.imooc.aop.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 匹配AOP对象的目标对象为指定类型的方法,即LogService的aop代理对象的方法
 * 注解@Pointcut("this(com.imooc.log.ILogger)")
 *
 * 匹配实现Loggable接口的目标对象(而不是aop代理后的对象)的方法
 * 注解@Pointcut("target(com.imooc.log.ILogger)")
 *
 * Introduction为类自动生成方法
 * this拦截DeclareParents(Introduction)
 * target不拦截DeclareParents(Introduction)
 *
 * 匹配所有以Service结尾的bean里头的方法
 * 注解@Pointcut("bean(*Service)")
 */
@Aspect
@Component
public class ObjectAspectConfig {
    @Pointcut("bean(logService)")
//    @Pointcut("target(com.imooc.aop.service.log.ILogger)")
    public void matchCondition(){}

    @Before("matchCondition()")
    public void before(){
        System.out.println("ObjectAspectConfig");
        System.out.println("###before");
    }
}
