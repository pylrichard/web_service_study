package com.bd.imooc.security.core.social;

import com.bd.imooc.security.core.properties.SecurityProperties;
import com.bd.imooc.security.core.social.support.ImoocSpringSocialConfigurer;
import com.bd.imooc.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社交登录配置主类
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        /*
            执行同目录下的SQL创建UserConnection表
            业务系统用户与第三方应用用户的关联关系存放在数据库中
        */
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator,
                Encryptors.noOpText());
        repository.setTablePrefix("imooc_");
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }

        return repository;
    }

    /**
     * 第三方登录配置类，供浏览器或App模块引入第三方登录配置使用
     *
     * 添加SocialAuthenticationFilter到过滤器链
     */
    @Bean
    public SpringSocialConfigurer imoocSocialSecurityConfig() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        ImoocSpringSocialConfigurer configurer = new ImoocSpringSocialConfigurer(filterProcessesUrl);
        //App/Browser注册跳转地址不同，App的SpringSocialConfigurerPostProcessor中修改
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);

        return configurer;
    }

    /**
     * 处理注册流程的工具类
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));
    }
}
