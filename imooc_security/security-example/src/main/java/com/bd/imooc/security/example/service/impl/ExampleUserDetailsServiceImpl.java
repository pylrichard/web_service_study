package com.bd.imooc.security.example.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ExampleUserDetailsServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录用户名:" + username);

        /*
            根据用户名到DB中查找用户信息
            根据查找到的用户信息判断用户是否被锁定
         */

        //模拟密码加密(同一个密码每次加密得到结果不一样，使用了随机生成的salt)，用户注册时进行加密，此处是用户登录获取DB中的用户信息
        String password = passwordEncoder.encode("Pyl123456");
        logger.info("数据库密码是:" + password);
        return new User(username, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

}
