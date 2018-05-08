package com.bd.roncoo.spring.boot.primer.controller;

import com.bd.roncoo.spring.boot.primer.bean.Result;
import com.bd.roncoo.spring.boot.primer.bean.User;
import com.bd.roncoo.spring.boot.primer.bean.UserLog;
import com.bd.roncoo.spring.boot.primer.cache.UserLogCache;
import com.bd.roncoo.spring.boot.primer.enums.ResultEnum;
import com.bd.roncoo.spring.boot.primer.exception.RoncooException;
import com.bd.roncoo.spring.boot.primer.util.ResultUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Value(value = "${rsb.secret}")
    private String secret;
    @Value(value = "${rsb.number}")
    private int number;
    @Value(value = "${rsb.desc}")
    private String desc;

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
        Map<String, Object> map = new HashMap<>(16);
        map.put("name", name);
        map.put("value", "hello spring boot");
        map.put("secret", secret);
        map.put("number", number);
        map.put("desc", desc);

        return map;
    }

    @PostMapping("add_user")
    public Result<User> addUser(@Valid User user, BindingResult bindingResult) throws Exception {
        //User中有@NotBlank的name为空，此处打印提示信息
        if (bindingResult.hasErrors()) {
            //HttpAspect.doAfterReturning的参数object是此处的返回结果
            return ResultUtil.fail(ResultEnum.PARAM_NOTBLANK.getCode(), bindingResult.getFieldError().getDefaultMessage());
            //返回null，触发Null异常，ExceptionHandle返回ResultEnum.UNKNOWN_ERROR
//            return null;
        }

        int age = user.getAge();
        if (age <= 18) {
            throw new RoncooException(ResultEnum.SCHOOL);
        } else if (age > 18 && age < 25) {
            throw new RoncooException(ResultEnum.UNIVERSITY);
        } else {
            return ResultUtil.success(user);
        }
    }

    @ApiOperation(value = "查找", notes = "根据用户ID查找用户")
    @GetMapping("select")
    public UserLog get(@ApiParam("用户ID") @RequestParam(defaultValue = "1") Integer id) {
        return userLogCache.selectById(id);
    }

    @ApiIgnore
    @GetMapping("update")
    public UserLog update(@RequestParam(defaultValue = "1") Integer id) {
        UserLog bean = userLogCache.selectById(id);

        bean.setUserName("pyl");
        bean.setCreateTime(new Date());
        userLogCache.updateById(bean);

        return bean;
    }

    @GetMapping("del")
    public void del(@RequestParam(defaultValue = "1") Integer id) {
        userLogCache.deleteById(id);
    }

    @PostMapping("update")
    public UserLog update(@RequestBody JsonNode jsonNode) {
        UserLog bean = userLogCache.selectById(jsonNode.get("id").asInt(1));

        System.out.println("jsonNode = " + jsonNode);
        if(bean == null){
            bean = new UserLog();
        }
        bean.setUserName("pyl");
        bean.setCreateTime(new Date());
        bean.setUserIp("192.168.8.10");
        userLogCache.updateById(bean);

        return bean;
    }

    @GetMapping("update/{id}")
    public UserLog updateById(@PathVariable(value = "id") Integer id) {
        UserLog bean = userLogCache.selectById(id);

        if(bean == null){
            bean = new UserLog();
        }
        bean.setUserName("syl");
        bean.setCreateTime(new Date());
        bean.setUserIp("192.168.8.11");
        userLogCache.updateById(bean);

        return bean;
    }

    /**
     * redis-cli中执行keys '*sessions*'
     * spring:session:expirations:之后为失效时间
     * spring:session:sessions:之后为session id
     *
     * 在多个Java应用间共享session，只需在另外的Java工程中进行同样的配置即可
     *
     * @return session id
     */
    @RequestMapping("uid")
    public String getUUID(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");

        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);

        return session.getId();
    }
}
