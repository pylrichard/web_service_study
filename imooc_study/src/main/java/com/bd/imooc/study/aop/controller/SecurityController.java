package com.bd.imooc.study.aop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * /common和/common_user都可以访问到commonAccess()
     */
    @RequestMapping(value = {"/common", "/common_user"})
    public String commonAccess(){
        return "only login can view";
    }

    @RequestMapping("/admin")
    //实现权限控制，使用隐身模式访问http://192.168.8.10:8080/admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(){
        return "only admin can access";
    }
}
