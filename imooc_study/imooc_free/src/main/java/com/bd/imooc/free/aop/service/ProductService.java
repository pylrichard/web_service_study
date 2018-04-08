package com.bd.imooc.free.aop.service;

import com.bd.imooc.free.aop.annotation.AdminOnly;
import com.bd.imooc.free.aop.annotation.NeedSecured;
import com.bd.imooc.free.aop.annotation.NeedSecuredClass;
import com.bd.imooc.free.aop.annotation.NeedSecuredSource;
import com.bd.imooc.free.aop.domain.Product;
import com.bd.imooc.free.aop.service.log.ILogger;
import org.springframework.stereotype.Service;

@Service
//AOP注解匹配
@NeedSecured
@NeedSecuredClass
@NeedSecuredSource
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

    public void throwException() throws IllegalAccessException{
        System.out.println("execute throw exception");
        throw new IllegalAccessException("TEST");
    }

    public String getName() {
        System.out.println("execute get name");
        return "product service";
    }

    public void log() {
        System.out.println("log from product service");
    }
}
