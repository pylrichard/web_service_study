package com.bd.roncoo.book.shop.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
//HTTP请求权限判断
@EnableWebSecurity
//方法调用权限判断
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
                /*
                    浏览器的Cookie里保存Session Id，根据Id在服务器保存的众多Session中找到对应的Session
                    实现一个用户的多个请求共享一个Session
                    同一时间只允许服务器上存在1个session，如果在另外的IP地址登录，要么从当前session注销，要么不允许登录
                */
                .sessionManagement()
                //防止Session固定攻击，登录成功后改变Session Id
                .sessionFixation().changeSessionId()
                //application.properties设置session有效时长server.session.timeout = 10
                .invalidSessionUrl("/session.html")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .and()
                .and()
                //防止跨站请求伪造
                .csrf()
                //将Token写入Cookie，在客户端通过JS从Cookie中读取Token，通过Ajax发送给服务器进行认证
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                /*
                    验证请求
                    antMatchers("/book/*") 以/book开头的请求所有用户都可以访问
                    antMatchers(HttpMethod.GET) 所有GET请求都可以访问
                    将/login.html添加为不需要认证，防止进入死循环
                    authorizeRequests.accessDecisionManager()设置ConsensusBased等，默认是AffirmativeBased
                */
                .authorizeRequests()
                .antMatchers("/book", "/login.html", "/auth", "/session.html").permitAll()
                //在代码中配置权限非常麻烦，也不灵活，修改代码都需要重启服务
//				.antMatchers("/book/*").access("hasRole('a') or hasRole('b')")
                //其它请求都需要管理员权限
                .anyRequest().access("@bookSecurity.check(authentication, request)");
    }
}
