package com.bd.roncoo.eshop.user.service.service;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.user.service.dao.RedisDAO;
import com.bd.roncoo.eshop.user.service.mapper.UserMapper;
import com.bd.roncoo.eshop.user.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Resource
    private RedisDAO redisDAO;

    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }

    public User findUserInfo(Long id) {
        return userMapper.findUserInfo(id);
    }

    public User getCachedUserInfo() {
        redisDAO.set("cached_user_pyl", "{\"name\": \"pyl\", \"age\":30}");
        String userJSON = redisDAO.get("cached_user_pyl");
        JSONObject userJSONObject = JSONObject.parseObject(userJSON);
        User user = new User();
        user.setName(userJSONObject.getString("name"));
        user.setAge(userJSONObject.getInteger("age"));

        return user;
    }

}
