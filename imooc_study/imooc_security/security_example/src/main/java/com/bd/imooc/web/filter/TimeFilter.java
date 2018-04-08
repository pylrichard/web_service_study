package com.bd.imooc.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * web.xml中通过<filter>可配置第三方Filter(没有标注@Component)，但Spring Boot没有web.xml
 * Spring Boot中第三方Filter的注入见WebConfig.timeFilter()
 *
 * 过滤器拦截REST服务，只能获取HTTP的请求和响应
 * 请求由Controller的哪个方法处理，是无法获取的
 * Filter是JavaEE定义的规范，不知道Spring的Controller
 *
 * 使用拦截器(Spring提供)可以实现
 */
//@Component
public class TimeFilter implements Filter {
    @Override
    public void destroy() {
        System.out.println("time filter destroy");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("time filter start");
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        System.out.println("time filter 耗时:"+ (System.currentTimeMillis() - start));
        System.out.println("time filter finish");
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        System.out.println("time filter init");
    }
}
