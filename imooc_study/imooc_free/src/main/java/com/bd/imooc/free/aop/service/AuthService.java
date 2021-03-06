package com.bd.imooc.free.aop.service;

import com.bd.imooc.free.aop.security.CurrentUserHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    public void checkAccess() {
        String user = CurrentUserHolder.get();
        if (!user.equals("admin")) {
            throw new RuntimeException("不是admin用户，操作不允许");
        }
    }
}
