package com.bd.imooc.security.rbac.authorization;

import com.bd.imooc.security.core.authorization.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
//anyRequest()配置会被之后的配置覆盖，此处需要最终生效
@Order(Integer.MAX_VALUE)
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(HttpMethod.GET, "/fonts/**")
                .permitAll()
                .antMatchers(HttpMethod.GET,
                        "/**/*.html",
                        "/admin/me",
                        "/resource")
                .authenticated()
                .anyRequest()
                //连接Spring Security和security_rbac权限模块，进行url权限认证
                .access("@rbacService.hasPermission(request, authentication)");

        return true;
    }
}
