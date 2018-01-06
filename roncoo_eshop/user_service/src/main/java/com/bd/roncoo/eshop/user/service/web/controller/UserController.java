package com.bd.roncoo.eshop.user.service.web.controller;

import com.bd.roncoo.eshop.user.service.model.User;
import com.bd.roncoo.eshop.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/findAllUsers")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/findUserInfo")
    public User findUserInfo(Long id) {
        return userService.findUserInfo(id);
    }

    @GetMapping("/getCachedUserInfo")
    public User getCachedUserInfo() {
        return userService.getCachedUserInfo();
    }
}
