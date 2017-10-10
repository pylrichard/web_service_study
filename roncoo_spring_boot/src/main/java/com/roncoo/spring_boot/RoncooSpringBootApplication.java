package com.roncoo.spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RoncooSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoncooSpringBootApplication.class, args);
    }
}
