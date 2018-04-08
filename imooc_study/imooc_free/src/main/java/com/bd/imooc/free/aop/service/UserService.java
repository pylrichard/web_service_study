package com.bd.imooc.free.aop.service;

import com.bd.imooc.free.aop.config.ApplicationContextHolder;
import com.bd.imooc.free.aop.dao.UserDAO;
import com.bd.imooc.free.aop.dao.UserLogDAO;
import com.bd.imooc.free.aop.domain.User;
import com.bd.imooc.free.aop.domain.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    UserLogDAO userLogDAO;

    /**
     * 没有@Transactional，两次执行UserServiceTest.addUser，因为User的唯一键name触发异常
     * 导致UserLog插入成功，User插入失败
     *
     * org.springframework.transaction.interceptor.TransactionInterceptor.invoke设置断点
     * 观察事务控制过程
     *
     * invokeWithinTransaction
     * proceedWithInvocation
     * 触发异常对事务进行回滚
     * completeTransactionAfterThrowing
     * 提交事务
     * commitTransactionAfterReturning
     */
    @Transactional
    public void addUser(String name){
        UserLog log = new UserLog();
        log.setContent("create user: " + name);
        userLogDAO.save(log);

        User user = new User();
        user.setName(name);
        userDAO.save(user);
    }

    /**
     * 见探秘Spring AOP的39~43.png
     *
     * org.springframework.cache.aspectj.AnnotationCacheAspect中定义@Cacheable的pointcut
     *
     * 1 org.springframework.aop.framework.CglibAopProxy
     *
     * 2 org.springframework.cache.interceptor.CacheInterceptor.invoke
     * execute设置断点
     *
     * 3 org.springframework.cache.interceptor.CacheAspectSupport 进行缓存控制
     * protected Object execute设置断点
     *
     * private Object execute设置断点
     *
     * 3.1 查询是否已经缓存返回结果
     * findCachedItem
     * findInCaches
     * AbstractCacheInvoker.doGet
     *
     * 3.2 如果没有缓存命中，则生成Put请求，将返回结果放入缓存
     * collectPutRequests
     *
     * 3.3 如果有缓存命中，则返回缓存结果
     * wrapCacheValue
     *
     * 将DB返回结果放入缓存
     * 3.4 cachePutRequest.apply
     */
    @Cacheable(cacheNames = {"user"})
    public List<String> getUserList(){
        System.out.println("");
        System.out.println("mock: get from db");

        return Arrays.asList("pyl", "admin");
    }

    public List<String> innerCall(){
        //内部调用方法，AOP缓存命中无法执行
//        return this.getUserList();

        //使用UserService代理调用方法，AOP缓存命中可以执行
        UserService proxy = ApplicationContextHolder.getContext().getBean(UserService.class);
        return proxy.getUserList();
    }
}
