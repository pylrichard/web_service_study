package com.bd.roncoo.book.shop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 执行schema-mysql.sql创建相应数据库，注意大写
 * application.properties先设置端口号为8060，启动一个服务进程
 * 设置端口号为8061，再启动一个服务进程
 * 此时先访问http://localhost:8060/admin/，登录成功后
 * 再访问http://localhost:8061/admin/，不需要再次登录
 * 注解@EnableJdbcHttpSession实现集群session状态共享
 */
@SpringBootApplication
@EnableSwagger2
@EnableJdbcHttpSession
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
