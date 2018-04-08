package com.bd.imooc.free.aop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
//为了使用@PreAuthorize
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 见探秘Spring AOP的33~38.png
     *
     * 下面的.anyRequest().authenticated()设置断点
     *
     * org.springframework.security.access.intercept.AbstractSecurityInterceptor
     * beforeInvocation的this.accessDecisionManager.decide设置断点
     *
     * org.springframework.security.access.vote.AffirmativeBased
     * decide的getDecisionVoters设置Watch
     *
     * voter.vote进行投票，决定返回ACCESS_GRANTED还是ACCESS_DENIED
     *
     * 第一次触发vote.vote的断点只有1个voter(WebExpressionVoter)，deny > 1，要求输入用户名和密码，输入admin/admin
     *
     * 第二次触发断点，PreInvocationAuthorizationAdviceVoter判断ROLE权限
     *
     * org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter
     * vote的findPreInvocationAttribute查找权限要求，得到@PreAuthorize设置的hasRole("ROLE_ADMIN")
     *
     * org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice
     * before的ExpressionUtils.evaluateAsBoolean判断是否允许登录
     * 返回ACCESS_GRANTED
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //index请求不需要校验
                .antMatchers("/", "/index").permitAll()
                //其他请求需要校验
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //添加用户信息
        auth.inMemoryAuthentication()
                .withUser("pyl").password("pyl").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
    }
}
