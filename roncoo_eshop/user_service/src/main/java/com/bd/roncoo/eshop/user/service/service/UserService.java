package com.bd.roncoo.eshop.user.service.service;

import com.bd.roncoo.eshop.user.service.mapper.UserMapper;
import com.bd.roncoo.eshop.user.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }
}
