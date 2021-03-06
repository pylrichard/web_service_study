package com.bd.imooc.security;

import com.bd.imooc.security.core.authorization.AuthorizeConfigProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ExampleAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //以下URL和方法需要管理员权限
        config.antMatchers(HttpMethod.GET, "/user/*")
                .access("hasRole('ADMIN') and hasIpAddress('192.168.8.10')");

        return false;
    }
}
