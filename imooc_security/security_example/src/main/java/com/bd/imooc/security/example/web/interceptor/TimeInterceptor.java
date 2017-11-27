package com.bd.imooc.security.example.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求由Controller的哪个方法处理，通过拦截器得知
 *
 * 拦截器无法获取方法参数信息，见DispatcherServlet.doDispatch()
 *
 * 使用AOP可以实现获取方法参数信息
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("preHandle");
        /*
            打印Controller方法信息
         */
        System.out.println(((HandlerMethod) handler).getBean().getClass().getName());
        System.out.println(((HandlerMethod) handler).getMethod().getName());
        //保存开始时间
        request.setAttribute("startTime", System.currentTimeMillis());

        //返回false，不会执行Controller的方法，postHandle()等也不会执行
        return true;
    }

    /**
     * Controller的方法触发异常，不会执行postHandle()
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("time interceptor 耗时:" + (System.currentTimeMillis() - start));
    }

    /**
     * Controller的方法触发异常，也会执行afterCompletion()
     * UserNotExistException会被ControllerExceptionHandler拦截
     * RuntimeException会被TimeInterceptor拦截
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("time interceptor 耗时:" + (System.currentTimeMillis() - start));
        System.out.println("ex is " + ex);
    }
}
