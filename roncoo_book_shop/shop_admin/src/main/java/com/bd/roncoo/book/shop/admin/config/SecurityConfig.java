package com.bd.roncoo.book.shop.admin.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //以表单的形式验证登录
        http.formLogin()
                .and()
                //验证请求
                //antMatchers("/book/*") 以/book开头的请求所有用户都可以访问
                //antMatchers(HttpMethod.GET) 所有GET请求都可以访问
                .authorizeRequests().antMatchers("/book").permitAll()
                //其它请求都需要验证
                .anyRequest().authenticated();
    }
}
