package com.imooc.aop.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdviceAspectConfig {
    @Pointcut("@annotation(com.imooc.aop.annotation.AdminOnly) && within(com.imooc..*)")
    public void matchAnno(){}

    @Pointcut("execution(* *..delete*(Long)) && within(com.imooc..*) ")
    public void matchLongArg(){}

    @Pointcut("execution(public * com.imooc.aop.service..*Service.*(..) throws java.lang.IllegalAccessException) && within(com.imooc..*)")
    public void matchException(){}

    @Pointcut("execution(String com.imooc..*.*(..)) && within(com.imooc..*)")
    public void matchReturn(){}

    /*
        Advice
     */
    @Before("matchLongArg() && args(id)")
    public void before(Long id){
        System.out.println("AdviceAspectConfig");
        System.out.println("###before，get args:"+ id);
    }

    //执行ProductServiceTest.delete2
//    @After("matchAnno()")
    //执行ProductServiceTest.throwException
    @AfterThrowing("matchException()")
    public void after() {
        System.out.println("AdviceAspectConfig");
        System.out.println("###after");
    }

    //执行ProductServiceTest.getName
    @AfterReturning(value = "matchReturn()", returning = "result")
    public void afterReturning(Object result) {
        System.out.println("AdviceAspectConfig");
        System.out.println("###afterReturning result " + result);
    }

    @Around("matchAnno()")
//    @Around("matchException()")
    public Object around(ProceedingJoinPoint joinPoint){
        System.out.println("AdviceAspectConfig");
        System.out.println("###around before");

        Object result = null;
        try {
            //调用目标方法
            result = joinPoint.proceed(joinPoint.getArgs());
            System.out.println("###around returning");
        } catch (Throwable e) {
            System.out.println("###ex");
            e.printStackTrace();
        } finally {
            System.out.println("###finally");
        }

        return result;
    }
}
