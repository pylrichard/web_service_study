package com.bd.imooc.study.aop.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 匹配ProductService类里头的所有方法
 * 注解@Pointcut("within(com.imooc.service.ProductService)")
 * 匹配com.imooc包及子包下所有类的方法
 * 注解@Pointcut("within(com.imooc..*)")
 */
@Aspect
@Component
public class PkgTypeAspectConfig {
//    @Pointcut("within(com.imooc.aop.service.sub.*)")
    @Pointcut("within(com.bd.imooc.study.aop.service.ProductService)")
    public void matchType(){}

    @Before("matchType()")
    public void before(){
        System.out.println("PkgTypeAspectConfig");
        System.out.println("###before");
    }
}
