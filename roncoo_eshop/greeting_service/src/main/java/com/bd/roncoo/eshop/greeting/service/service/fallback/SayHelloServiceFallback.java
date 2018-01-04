package com.bd.roncoo.eshop.greeting.service.service.fallback;

import com.bd.roncoo.eshop.greeting.service.service.SayHelloService;
import org.springframework.stereotype.Component;

@Component
public class SayHelloServiceFallback implements SayHelloService {
    @Override
    public String sayHello(String name) {
        return "error, " + name;
    }
}
