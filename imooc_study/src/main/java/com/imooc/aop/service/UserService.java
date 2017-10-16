package com.imooc.aop.service;

import com.imooc.aop.dao.UserDAO;
import com.imooc.aop.dao.UserLogDAO;
import com.imooc.aop.domain.User;
import com.imooc.aop.domain.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
}
