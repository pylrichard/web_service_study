package com.bd.imooc.security.example.web.config;

import com.bd.imooc.security.example.web.filter.TimeFilter;
import com.bd.imooc.security.example.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TimeFilter timeFilter = new TimeFilter();
        registrationBean.setFilter(timeFilter);
        /*
            配置url匹配规则，过滤器生效
         */
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);

        return registrationBean;

    }
}
