package com.imooc.mmall.service;

import com.imooc.mmall.common.ServerResponse;
import com.imooc.mmall.pojo.User;

public interface UserService {
    ServerResponse<User> login(String userName, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValidity(String str, String type);

    ServerResponse<String> findPasswordQuestion(String userName);

    ServerResponse<String> checkPasswordAnswer(String userName, String question, String answer);
}
