package com.bd.roncoo.book.shop.common.lock;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Profile("!junit")
public class GlobalLockAspect {
    @Autowired
    private RedissonClient redisSon;

    @Autowired
    private CacheManager cacheManager;

    private static final String pathSeparator = ":";

    private static final String lockPathPrefix = "lock";

    private static final Logger logger = LoggerFactory.getLogger(GlobalLockAspect.class);

    /**
     * 使用分布式锁保证相同的lockPath被并发请求时，在分布式环境中只会被一个JVM处理
     * <p>
     * 当多个线程试图获取同一个锁时，未获取到锁的线程会抛出DistributedLockConflictException
     */
    @Around("@annotation(com.bd.roncoo.book.shop.common.lock.GlobalLock)")
    public Object invokeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取要调用的方法
        Method method = getMethod(joinPoint);
        //获取方法上的注解@GlobalLock
        GlobalLock distributedLock = method.getAnnotation(GlobalLock.class);
        String lockPath = parseLockPath(joinPoint, method, distributedLock);
        //从缓存中获取锁
        RLock lock = getLockFromCache(lockPath);
        //试图加锁，如果有3个实例并发请求，只有1个能加锁成功
        if (lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime() * 1000, TimeUnit.MILLISECONDS)) {
            if (logger.isDebugEnabled()) {
                logger.debug("get lock on path:" + lockPath);
            }
            try {
                //获取锁成功后执行方法
                return joinPoint.proceed();
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    if (logger.isDebugEnabled()) {
                        logger.debug("release lock on path:" + lockPath);
                    }
                }
            }
        } else {
            //没有获取锁成功的实例抛出异常
            throw new GlobalLockConflictException(lockPath);
        }
    }

    /**
     * 解析锁的路径
     */
    private String parseLockPath(ProceedingJoinPoint joinPoint, Method method, GlobalLock distributedLock) {
        String lockPath = distributedLock.path();
        if (StringUtils.isBlank(lockPath)) {
            lockPath = joinPoint.getTarget().getClass().getSimpleName() + pathSeparator + method.getName();
        }
        if (StringUtils.isNotBlank(distributedLock.key())) {
            String key = parseKey(distributedLock.key(), method, joinPoint.getArgs());
            lockPath = lockPath + pathSeparator + key;
        }
        lockPath = lockPathPrefix + pathSeparator + lockPath;
        if (logger.isDebugEnabled()) {
            logger.debug("lock path is:" + lockPath);
        }

        return lockPath;
    }

    /**
     * 缓存保存锁来保证可重入锁可以被重复使用
     */
    private RLock getLockFromCache(String lockPath) {
        Cache cache = cacheManager.getCache("Lock");
        RLock lock = null;
        if (cache != null) {
            lock = cache.get(lockPath, RLock.class);
        }
        if (lock == null) {
            lock = redisSon.getLock(lockPath);
            if (cache != null) {
                cache.put(lockPath, lock);
            }
        }

        return lock;
    }

    /**
     * 获取锁的路径
     * lockPath定义在注解上，支持SPEL表达式
     */
    private String parseKey(String lockPath, Method method, Object[] args) {
        Assert.hasText(lockPath, "lockPath can't be empty");
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        //使用SPEL进行key的解析
        SpelExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }

        return parser.parseExpression(lockPath).getValue(context, String.class);
    }

    /**
     * 获取被拦截方法对象
     * MethodSignature.getMethod()获取的是顶层接口或者父类的方法对象，而缓存的注解在实现类的方法上
     * 所以使用反射获取当前对象的方法对象
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getMethod(signature.getName(), method.getParameterTypes());
            } catch (SecurityException e) {
                if (logger.isDebugEnabled()) {
                    logger.error("lockPoint getMethod", e);
                }
            } catch (NoSuchMethodException e) {
                if (logger.isDebugEnabled()) {
                    logger.error("lockPoint getMethod", e);
                }
            }
        }

        return method;
    }
}
