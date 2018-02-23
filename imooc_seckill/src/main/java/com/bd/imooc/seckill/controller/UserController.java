package com.bd.imooc.seckill.controller;

import com.bd.imooc.seckill.domain.SecKillUser;
import com.bd.imooc.seckill.result.Result;
import com.bd.imooc.seckill.service.RedisService;
import com.bd.imooc.seckill.service.SecKillUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    SecKillUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<SecKillUser> info(Model model, SecKillUser user) {
        return Result.success(user);
    }
}
