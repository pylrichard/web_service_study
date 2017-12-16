package com.bd.roncoo.book.shop.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService adminUserDetailService;
    @Autowired
    private AuthenticationSuccessHandler bookShopAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler bookShopAuthenticationFailureHandler;
    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        /*
            persistent_logins表只需要创建一次即可
            tokenRepository.setCreateTableOnStartup(true)
        */
        tokenRepository.setDataSource(dataSource);

        return tokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminUserDetailService)
                //指定密码编码器
                .passwordEncoder(new BCryptPasswordEncoder());
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            Basic认证把用户名+冒号+密码用Base64算法加密后的字符串放在Http Request的Header Authorization中发送给服务器
            httpBasic()把BasicAuthenticationFilter添加到过滤器链中
        */
        http.httpBasic()
                .and()
                //formLogin()把UsernamePasswordAuthenticationFilter添加到过滤器链中
                .formLogin()
                //自定义登录页面
                .loginPage("/login.html")
                //login.html中不使用login作为action名称
                .loginProcessingUrl("/auth")
                /*
                    username和password是Spring Security中UsernamePasswordAuthenticationFilter默认的字段
                    使用自定义用户名和密码字段名
                */
                .usernameParameter("user")
                .passwordParameter("pass")
                .successHandler(bookShopAuthenticationSuccessHandler)
                .failureHandler(bookShopAuthenticationFailureHandler)
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository())
                //设置token有效时长
                .tokenValiditySeconds(60)
                .and()
                .csrf().disable()
                /*
                    验证请求
                    antMatchers("/book/*") 以/book开头的请求所有用户都可以访问
                    antMatchers(HttpMethod.GET) 所有GET请求都可以访问
                    将/login.html添加为不需要认证，防止进入死循环
                */
                .authorizeRequests().antMatchers("/book", "/login.html", "/auth").permitAll()
                //其它请求都需要验证
                .anyRequest().authenticated();
    }
}
