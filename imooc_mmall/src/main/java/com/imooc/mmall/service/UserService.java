package com.imooc.mmall.service;

import com.imooc.mmall.common.ServerResponse;
import com.imooc.mmall.pojo.User;

public interface UserService {
    /**
     * 用户登录
     */
    ServerResponse<User> login(String userName, String password);
}
