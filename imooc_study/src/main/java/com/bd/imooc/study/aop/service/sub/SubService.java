package com.bd.imooc.study.aop.service.sub;

import com.bd.imooc.study.aop.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class SubService extends ProductService {
    public void method(){
        System.out.println("execute sub service method");
    }
}
