package com.bd.imooc.study.aop.security;

import com.bd.imooc.study.aop.service.AuthService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {
    @Autowired
    private AuthService authService;

    @Pointcut("@annotation(com.bd.imooc.study.aop.annotation.AdminOnly)")
    public void adminOnly() {}

    @Before("adminOnly()")
    public void check() {
        authService.checkAccess();
    }
}
