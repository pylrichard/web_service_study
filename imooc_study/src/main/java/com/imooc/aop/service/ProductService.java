package com.imooc.aop.service;

import com.imooc.aop.domain.Product;
import com.imooc.aop.annotation.AdminOnly;
import com.imooc.aop.service.log.ILogger;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements ILogger {
    @AdminOnly
    public void insert(Product product) {
        System.out.println("execute product service insert");
        //使用AOP实现权限控制后，不需要侵入代码
//        authService.checkAccess();
    }

    @AdminOnly
    public void delete(Long id) {
        System.out.println("execute product service delete");
    }

    public void getName() throws IllegalAccessException{
        System.out.println("execute get name");
        throw new IllegalAccessException("TEST");
    }

    public void log() {
        System.out.println("log from product service");
    }
}
