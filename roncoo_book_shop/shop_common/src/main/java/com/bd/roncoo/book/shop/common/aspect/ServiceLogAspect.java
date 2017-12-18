package com.bd.roncoo.book.shop.common.aspect;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Collection;

/**
 * 拦截器在Web应用中拦截HTTP请求进行日志记录
 * 服务提供者是通过RPC调用提供服务，无法使用拦截器，使用AOP切片进行日志记录
 */
@Component
@Aspect
public class ServiceLogAspect {
    protected static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * ProceedingJoinPoint是要拦截的方法的封装类
     * 切入点包括调用之前、调用之后、抛出异常
     * <p>
     * execution后第1个参数表示要拦截方法可见性，*表示所有方法可见
     * ..*.*(..):
     * 1 ..*表示所有子包，如果是.*表示第一层子包
     * 2 .*表示任意类
     * 3 (..)表示任意参数
     * <p>
     * 可以将记录的日志导入EFK集群进行分析
     */
//    @Around("execution(* com.bd.roncoo.book.shop.common.service..*.*(..))")
    @Around("@annotation(com.bd.roncoo.book.shop.common.aspect.ServiceLog)")
    public Object logServiceInvoke(ProceedingJoinPoint point) throws Throwable {
        return doLog(point);
    }

    protected Object doLog(ProceedingJoinPoint point) throws Throwable {
        if (logger.isInfoEnabled()) {
            logger.info("*****call service begin: " + point.getSignature().toLongString() + "*****");

            for (Object arg : point.getArgs()) {
                printObj(arg, "service parameter: ");
            }

            try {
                /*
                    调用要拦截的方法
                 */
                Object retVal = point.proceed();
                printObj(retVal, "return value: ");

                return retVal;
            } catch (Throwable e) {
                logger.info("throw exception", e);
                throw e;
            } finally {
                logger.info("*****call service end*****");
            }
        }

        return point.proceed();
    }

    /**
     * 记录参数
     */
    @SuppressWarnings("rawtypes")
    void printObj(Object arg, String prefix) {
        if (arg != null) {
            if (arg.getClass().isArray()) {
                if (ArrayUtils.isNotEmpty((Object[]) arg)) {
                    Object[] args = (Object[]) arg;

                    for (Object object : args) {
                        printObj(object, prefix);
                    }
                }
            } else if (arg instanceof Collection) {
                if (CollectionUtils.isNotEmpty((Collection) arg)) {
                    Collection collection = (Collection) arg;

                    for (Object object : collection) {
                        printObj(object, prefix);
                    }
                }
            }

            /*
                参数是基本类型或者基本类型的封装对象
             */
            if (ClassUtils.isPrimitiveOrWrapper(arg.getClass())) {
                logger.info(prefix + arg.toString());
            } else if (arg instanceof String) {
                logger.info(prefix + arg);
            } else {
                logger.info(prefix + ReflectionToStringBuilder.toString(arg));
            }
        } else {
            logger.info(prefix + " null");
        }
    }
}
