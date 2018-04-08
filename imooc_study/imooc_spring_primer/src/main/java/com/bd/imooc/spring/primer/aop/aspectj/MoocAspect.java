package com.bd.imooc.spring.primer.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MoocAspect {
    @Pointcut("execution(* com.bd.imooc.spring.aop.aspectj.biz.*Biz.*(..))")
    public void pointcut() {
    }

    @Pointcut("within(com.bd.imooc.spring.aop.aspectj.biz.*)")
    public void bizPointcut() {
    }

    /**
     * 见131.png，引用切入点
     */
    @Before("pointcut()")
    public void before() {
        System.out.println("Before.");
    }

    /**
     * arg是是MoocBiz.save的参数
     */
    @Before("pointcut() && args(arg)")
    public void beforeWithParam(String arg) {
        System.out.println("BeforeWithParam." + arg);
    }

    /**
     * 见MoocMethod和MoocBiz
     * value是MoocBiz save with MoocMethod.
     */
    @Before("pointcut() && @annotation(moocMethod)")
    public void beforeWithAnnotaion(MoocMethod moocMethod) {
        System.out.println("BeforeWithAnnotation." + moocMethod.value());
    }

    /**
     * 见132.png，引用切入点
     * retVal是MoocBiz.save的返回值
     */
    @AfterReturning(pointcut = "bizPointcut()", returning = "retVal")
    public void afterReturning(Object retVal) {
        System.out.println("AfterReturning : " + retVal);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowing(RuntimeException e) {
        System.out.println("AfterThrowing : " + e.getMessage());
    }

    @After("pointcut()")
    public void after() {
        System.out.println("After.");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Around Before.");
        Object obj = pjp.proceed();
        System.out.println("Around After.");
        System.out.println("Around Return: " + obj);

        return obj;
    }
}
