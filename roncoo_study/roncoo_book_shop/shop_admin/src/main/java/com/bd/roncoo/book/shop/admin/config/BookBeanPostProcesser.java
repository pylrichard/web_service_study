package com.bd.roncoo.book.shop.admin.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.stereotype.Component;

/**
 * BeanPostProcessor在所有Spring Bean组装完成后，对Bean进行处理
 */
@Component
public class BookBeanPostProcesser implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof UserInfoTokenServices) {
            /*
				替换FixedAuthoritiesExtractor和FixedPrincipalExtractor
				((UserInfoTokenServices)bean).setPrincipalExtractor(xxx)
				((UserInfoTokenServices)bean).setAuthoritiesExtractor(xxx)
			*/
            System.out.println(beanName);
        }

        return bean;
    }
}
