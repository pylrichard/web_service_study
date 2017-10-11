package com.roncoo.spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
//让Spring扫描到Servlet和Filter
@ServletComponentScan
@EnableCaching
@EnableJms
@ImportResource(locations = { "classpath:druid-bean.xml" })
public class RoncooSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoncooSpringBootApplication.class, args);
    }
}
