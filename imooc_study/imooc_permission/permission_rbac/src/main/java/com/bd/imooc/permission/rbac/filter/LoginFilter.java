package com.bd.imooc.permission.rbac.filter;

import com.bd.imooc.permission.rbac.common.RequestHolder;
import com.bd.imooc.permission.rbac.model.SysUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截登录请求，将用户信息和请求信息存储到线程缓存
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        SysUser sysUser = (SysUser) req.getSession().getAttribute("user");
        if (sysUser == null) {
            //前缀/表示signin.jsp在根路径下
            String path = "/signin.jsp";
            resp.sendRedirect(path);

            return;
        }
        RequestHolder.add(sysUser);
        RequestHolder.add(req);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
