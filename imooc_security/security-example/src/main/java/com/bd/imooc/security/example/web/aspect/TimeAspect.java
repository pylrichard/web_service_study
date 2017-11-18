package com.bd.imooc.security.example.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

/**
 * HTTP请求的拦截顺序是Tomcat->Filter->Interceptor->Aspect->Controller
 *
 * 异常的拦截顺序是Controller->Aspect->ControllerAdvice->Interceptor->Filter->Tomcat
 */
//@Aspect
//@Component
public class TimeAspect {
    @Around("execution(* com.bd.imooc.security.example.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("time aspect start");
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg is " + arg);
        }
        long start = System.currentTimeMillis();
        Object object = pjp.proceed();
        System.out.println("time aspect 耗时:" + (System.currentTimeMillis() - start));
        System.out.println("time aspect end");

        return object;
    }
}
