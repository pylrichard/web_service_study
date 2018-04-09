package com.bd.roncoo.book.shop.admin.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义授权策略
 * 可以注入服务，服务根据用户访问路径和拥有的权限，去数据库中查询用户是否拥有路径的访问权限
 */
@Component("bookSecurity")
public class BookSecurity {
    public boolean check(Authentication auth, HttpServletRequest req) {
        System.out.println(req.getRequestURI());
        Object principal = auth.getPrincipal();
        if (principal != null && principal instanceof UserDetails) {
            System.out.println(((UserDetails) principal).getAuthorities());
        }

        return true;
    }
}
