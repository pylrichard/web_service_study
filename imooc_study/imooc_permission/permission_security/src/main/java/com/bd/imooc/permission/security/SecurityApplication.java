package com.bd.imooc.permission.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/roleAuth")
    public String roleAuth() {
        return "roleAuth";
    }

    //方法调用前进行验证
    @PreAuthorize("#id < 10 and principal.username.equals(#userName) and #user.username.equals('pyl')")
    //方法调用后进行验证
    @PostAuthorize("returnObject % 2 == 0")
    @RequestMapping("/authorize")
    public Integer authorize(Integer id, String userName, User user) {
        return id;
    }

    @PreFilter("filterObject % 2 == 0")
    @PostFilter("filterObject % 4 == 0")
    @RequestMapping("/filter")
    public List<Integer> filter(List<Integer> idList) {
        return idList;
    }
}
