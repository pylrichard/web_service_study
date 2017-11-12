package com.bd.roncoo.spring.boot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {
    protected static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

//    @Pointcut("execution(public * com.bd.roncoo.spring.boot.controller.APIController.addUser(..))")
    //指定切入点，拦截APIController所有的方法，..表示任意参数
    @Pointcut("execution(public * com.bd.roncoo.spring.boot.controller.APIController.*(..))")
    public void pringLog() {}

    /**
     * 输出HTTP请求信息和类方法信息到日志
     *
     * @param joinPoint
     */
    @Before("pringLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("url={}", request.getRequestURL());
        //HTTP请求类型
        logger.info("method={}", request.getMethod());
        logger.info("ip={}", request.getRemoteAddr());
        //类名+类方法
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //方法参数
        logger.info("args={}", joinPoint.getArgs());
    }

    @After("pringLog()")
    public void doAfter() {
        logger.info("doAfter");
    }

    /**
     * 输出HTTP响应信息到日志
     *
     * @param object HTTP响应，对应于被拦截方法的返回结果
     */
    @AfterReturning(returning = "object", pointcut = "pringLog()")
    public void doAfterReturning(Object object) {
        logger.info("response={}", object.toString());
    }
}
