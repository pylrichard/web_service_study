package com.bd.imooc.study.aop.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 匹配方法标注有AdminOnly的注解的方法
 * 注解@Pointcut("@annotation(AdminOnly) && within(com.bd.imooc..*)")
 *
 * 匹配标注有NeedSecured的类底下的方法 RetentionPolicy要是CLASS
 * 注解@Pointcut("@within(NeedSecured) && within(com.bd.imooc..*)")
 *
 * 匹配标注有NeedSecured的类及其子类的方法 RetentionPolicy要是RUNTIME
 * 在Spring Context的环境下，二者没有区别
 * 注解@Pointcut("@target(NeedSecured) && within(com.bd.imooc..*)")
 *
 * 匹配传入的参数类标注有Repository注解的方法
 * 注解@Pointcut("@args(NeedSecured) && within(com.bd.imooc..*)")
 */
@Aspect
@Component
public class AnnoAspectConfig {
    //SubService(继承ProductService)的method也会被拦截
//    @Pointcut("@within(NeedSecured) && within(com.bd.imooc..*)")
//    @Pointcut("@annotation(AdminOnly)")
    @Pointcut("@args(com.bd.imooc.study.aop.annotation.NeedSecured) && within(com.bd.imooc..*)")
    public void matchAnno(){}

    @Before("matchAnno()")
    public void before(){
        System.out.println("AnnoAspectConfig");
        System.out.println("###before");
    }
}
