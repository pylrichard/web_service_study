package com.imooc.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootAOPApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAOPApplication.class, args);
    }
}
