package com.imooc.aop.service.log;

import com.imooc.aop.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class LogService implements ILogger {
    public void log() {
        System.out.println("log from LogService");
    }

    public void annoArg(Product product){
        System.out.println("execute log service annoArg");
    }
}
