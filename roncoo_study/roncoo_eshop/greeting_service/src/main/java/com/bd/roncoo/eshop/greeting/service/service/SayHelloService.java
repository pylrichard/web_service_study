package com.bd.roncoo.eshop.greeting.service.service;

import com.bd.roncoo.eshop.greeting.service.service.fallback.SayHelloServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "say_hello_service", fallback = SayHelloServiceFallback.class)
public interface SayHelloService {
    @GetMapping("/sayHello")
    String sayHello(@RequestParam(value = "name") String name);
}
