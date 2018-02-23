package com.bd.imooc.seckill.service;

import com.bd.imooc.seckill.dao.UserDao;
import com.bd.imooc.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }
}
