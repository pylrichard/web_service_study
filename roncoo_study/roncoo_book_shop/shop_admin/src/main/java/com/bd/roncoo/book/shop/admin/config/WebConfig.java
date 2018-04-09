package com.bd.roncoo.book.shop.admin.config;

import com.bd.roncoo.book.shop.admin.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private TimeInterceptor timeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

    /**
     * 注册过滤器，过滤器执行后才会执行拦截器
     */
    @Bean
    public FilterRegistrationBean characterEncodingFilterRegister() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8");
        filter.setForceEncoding(true);
        bean.setFilter(filter);

        List<String> urls = new ArrayList<>();
        urls.add("/*");
        bean.setUrlPatterns(urls);

        return bean;
    }
}
