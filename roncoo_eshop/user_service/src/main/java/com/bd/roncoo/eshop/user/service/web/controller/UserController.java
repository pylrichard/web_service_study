package com.bd.roncoo.eshop.user.service.web.controller;

import com.bd.roncoo.eshop.user.service.model.User;
import com.bd.roncoo.eshop.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUsers")
    @ResponseBody
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }
}
