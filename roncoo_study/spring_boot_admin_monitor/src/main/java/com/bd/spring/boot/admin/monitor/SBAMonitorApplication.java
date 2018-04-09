package com.bd.spring.boot.admin.monitor;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAdminServer
public class SBAMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SBAMonitorApplication.class, args);
    }
}
