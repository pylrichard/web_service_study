package com.bd.imooc.security.example.security;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class ExampleConnectionSignUp implements ConnectionSignUp {
    @Override
    public String execute(Connection<?> connection) {
        /*
            根据connection信息生成Example项目用户并返回用户id
            见JdbcUsersConnectionRepository.findUserIdsWithConnection()
         */
        return RandomStringUtils.randomNumeric(6);
    }
}
