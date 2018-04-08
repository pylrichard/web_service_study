package com.bd.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Example应用的包路径为com.bd.imooc，才能加载认证授权模块(com.bd.imooc.security)
 * 如果包路径为com.bd.imooc.xxx，则无法加载
 * scanBasePackages = com.bd.imooc.xxx指定加载包路径
 */
@SpringBootApplication
@RestController
@EnableSwagger2
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }
}