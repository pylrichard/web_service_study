package com.roncoo.spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableCaching
@EnableJms
public class RoncooSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoncooSpringBootApplication.class, args);
    }
}
