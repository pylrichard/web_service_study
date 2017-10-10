package com.roncoo.spring_boot.controller;

import com.roncoo.spring_boot.bean.User;
import com.roncoo.spring_boot.bean.UserLog;
import com.roncoo.spring_boot.cache.UserLogCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class APIController {
    @Value(value = "${rsb.secret}")
    private String secret;
    @Value(value = "${rsb.number}")
    private int number;
    @Value(value = "${rsb.desc}")
    private String desc;

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

    @Autowired
    private UserLogCache userLogCache;

    @RequestMapping
    public String api() {
        logger.debug("this is a log test, debug");
        logger.info("this is a log test, info");

        return "hello spring boot";
    }

    /**
     * 浏览器中输入192.168.8.10:8080/api/get?name=pyl
     * 返回json格式的结果
     */
    @RequestMapping(value = "get")
    public Map<String, Object> get(@RequestParam String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("value", "hello spring boot");
        map.put("secret", secret);
        map.put("number", number);
        map.put("desc", desc);

        return map;
    }

    @RequestMapping(value = "find/{id}/{name}")
    public User find(@PathVariable int id, @PathVariable String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setDate(new Date());

        return user;
    }

    @GetMapping(value = "select")
    public UserLog get(@RequestParam(defaultValue = "1") Integer id) {
        return userLogCache.selectById(id);
    }

    @GetMapping(value = "update")
    public UserLog update(@RequestParam(defaultValue = "1") Integer id) {
        UserLog bean = userLogCache.selectById(id);

        bean.setUserName("pyl");
        bean.setCreateTime(new Date());
        userLogCache.updateById(bean);

        return bean;
    }

    @GetMapping(value = "del")
    public void del(@RequestParam(defaultValue = "1") Integer id) {
        userLogCache.deleteById(id);
    }
}
