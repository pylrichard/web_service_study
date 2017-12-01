package com.bd.imooc.security.rbac.service.impl;

import com.bd.imooc.security.rbac.domain.Admin;
import com.bd.imooc.security.rbac.service.RbacService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Component("rbacService")
public class RbacServiceImpl implements RbacService {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof Admin) {
            if (StringUtils.equals(((Admin) principal).getUsername(), "admin")) {
                hasPermission = true;
            } else {
                //读取用户所拥有权限的所有URL
                Set<String> urls = ((Admin) principal).getUrls();
                for (String url : urls) {
                    /*
                        配置的url形式是/user/*，请求的url形式是/user/1
                        不能使用String.equals()，使用AntPathMatcher进行模式匹配
                     */
                    if (antPathMatcher.match(url, request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }

        return hasPermission;
    }
}
