package com.bd.imooc.permission.shiro.service;

import com.bd.imooc.permission.shiro.mapper.UserMapper;
import com.bd.imooc.permission.shiro.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }
}
