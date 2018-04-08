package com.bd.imooc.free.aop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void getUserList() throws Exception {
        System.out.println("call: " + userService.getUserList());
        //第二次从缓存中获取
        System.out.println("call: " + userService.getUserList());
    }

    @Test
    public void addUser() throws Exception {
        userService.addUser("pyl");
    }

    @Test
    public void innerCall(){
        System.out.println("call: " + userService.innerCall());
        System.out.println("call: " + userService.innerCall());
    }
}