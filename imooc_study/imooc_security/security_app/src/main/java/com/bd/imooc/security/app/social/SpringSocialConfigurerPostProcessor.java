package com.bd.imooc.security.app.social;

import com.bd.imooc.security.core.properties.SecurityConstants;
import com.bd.imooc.security.core.social.support.ImoocSpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 修改SocialConfig中设置的注册跳转地址
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "imoocSocialSecurityConfig")) {
            ImoocSpringSocialConfigurer config = (ImoocSpringSocialConfigurer) bean;
            config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);

            return config;
        }

        return bean;
    }
}
