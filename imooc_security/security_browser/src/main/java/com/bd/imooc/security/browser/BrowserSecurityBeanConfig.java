package com.bd.imooc.security.browser;

import com.bd.imooc.security.browser.logout.ImoocLogoutSuccessHandler;
import com.bd.imooc.security.browser.session.ImoocExpiredSessionStrategy;
import com.bd.imooc.security.browser.session.ImoocInvalidSessionStrategy;
import com.bd.imooc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
public class BrowserSecurityBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * session失效时的处理策略配置
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new ImoocInvalidSessionStrategy(securityProperties);
    }

    /**
     * 并发登录导致前一个session失效时的处理策略配置
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new ImoocExpiredSessionStrategy(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new ImoocLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }
}
