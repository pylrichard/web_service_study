package com.imooc.mmall.service.impl;

import com.imooc.mmall.common.ServerResponse;
import com.imooc.mmall.dao.UserMapper;
import com.imooc.mmall.pojo.User;
import com.imooc.mmall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String userName, String password) {
        int resultCount = userMapper.checkUsername(userName);
        if (resultCount == 0 ) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        /*
            TODO 密码进行MD5混淆
         */
        User user  = userMapper.selectLogin(userName, password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess("登录成功", user);
    }
}
