package com.bd.roncoo.book.shop.admin.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TimeInterceptor implements HandlerInterceptor {
    /**
     * REST API调用之前的处理
     * handler是处理HTTP请求的Java方法的抽象
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("in preHandle");
        System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
        System.out.println(((HandlerMethod)handler).getMethod().getName());

        request.setAttribute("startTime", System.currentTimeMillis());

        //返回false，handler不会被调用
        return true;
    }

    /**
     * REST API成功返回之后的处理，抛出异常本方法不会被调用
     * modelAndView保存返回的视图及视图数据，前后端不分离即服务器负责渲染页面时才有值，前后端分离时响应在HttpServletResponse的body中
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("in postHandle");
        System.out.println("execution time : "
                + (System.currentTimeMillis() - (long)request.getAttribute("startTime")) + "ms");
    }

    /**
     * REST API处理完成(分为2种情况:成功返回/抛出异常)之后的处理
     * ex在抛出异常时有值
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("in afterCompletion");
        System.out.println("execution time : "
                + (System.currentTimeMillis() - (long)request.getAttribute("startTime")) + "ms");
        System.out.println("exception is " + ex);
    }
}
