package com.bd.imooc.permission.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    /**
     * 认证配置
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //指定可登录的用户名、密码、角色
        auth.inMemoryAuthentication().withUser("pyl").password("Pyl123456").roles("ADMIN");
        //与inMemoryAuthentication()互斥
        auth.userDetailsService(securityUserDetailsService).passwordEncoder(new SecurityPasswordEncoder());
        //使用默认DB认证，表结构见users.ddl
        auth.jdbcAuthentication().usersByUsernameQuery("").authoritiesByUsernameQuery("")
                .passwordEncoder(new SecurityPasswordEncoder());
    }

    /**
     * HTTP请求配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin();
        http.csrf().disable();
    }

    /**
     * Web配置
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }
}
