package com.imooc.aop.service;

import com.imooc.aop.domain.Product;
import com.imooc.aop.security.AdminOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private AuthService authService;

    @AdminOnly
    public void insert(Product product) {
        System.out.println("execute product service insert");
        authService.checkAccess();
    }

    @AdminOnly
    public void delete(Long id) {
        System.out.println("execute product service delete");
        authService.checkAccess();
    }
}
