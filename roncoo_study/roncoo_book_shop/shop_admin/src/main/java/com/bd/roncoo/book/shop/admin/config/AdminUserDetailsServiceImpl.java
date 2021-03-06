package com.bd.roncoo.book.shop.admin.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = new BCryptPasswordEncoder().encode("Pyl123456");
        //生产环境下注入Repository，从数据库中读取用户信息
        if (StringUtils.equals(username, "pyl")) {
            return new User("pyl", password, AuthorityUtils.createAuthorityList("admin"));
        }

        return null;
    }
}