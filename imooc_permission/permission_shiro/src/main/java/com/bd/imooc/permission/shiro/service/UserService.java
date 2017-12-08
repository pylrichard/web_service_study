package com.bd.imooc.permission.shiro.service;

import com.bd.imooc.permission.shiro.model.User;

public interface UserService {
    User findByUserName(String userName);
}
