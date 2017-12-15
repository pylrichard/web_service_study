package com.bd.roncoo.book.shop.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService adminUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminUserDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            Basic认证把用户名+冒号+密码用Base64算法加密后的字符串放在Http Request的Header Authorization中发送给服务器
            httpBasic()把BasicAuthenticationFilter添加到过滤器链中
        */
        http.httpBasic()
                .and()
                //验证请求
                //antMatchers("/book/*") 以/book开头的请求所有用户都可以访问
                //antMatchers(HttpMethod.GET) 所有GET请求都可以访问
                .authorizeRequests().antMatchers("/book").permitAll()
                //其它请求都需要验证
                .anyRequest().authenticated();
    }
}
