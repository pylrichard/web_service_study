package com.bd.imooc.security.core.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //执行同目录下的SQL创建UserConnection表
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                                                                                connectionFactoryLocator,
                                                                                Encryptors.noOpText());
        repository.setTablePrefix("imooc_");

        return repository;
    }

    /**
     * 添加SocialAuthenticationFilter到过滤器链
     */
    @Bean
    public SpringSocialConfigurer imoocSocialSecurityConfig() {
        return new SpringSocialConfigurer();
    }
}
