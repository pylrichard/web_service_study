package com.bd.imooc.security.example.web.controller;

import com.bd.imooc.security.example.dto.User;
import com.bd.imooc.security.example.dto.UserQueryCondition;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 注解@RequestBody映射请求体到Java方法的参数
     *
     * 注解@Valid和BindingResult验证请求参数合法性并处理校验结果
     *
     * BindingResult需要@Valid配合，校验结果保存在BindingResult中
     *
     * 没有BindingResult，传入参数不合法就不会调用Controller方法，进入Spring默认错误处理机制
     */
    @PostMapping
    @ApiOperation(value = "创建用户")
    public User create(@Valid @RequestBody User user) {
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        //前端传入的时间戳参数会被解析为Date
        System.out.println(user.getBirthday());
        user.setId("1");

        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
//                FieldError fieldError = (FieldError)error
//                String message = fieldError.getField() + " " + error.getDefaultMessage()
                //显示@Past(message = "xxx")的值
                System.out.println(error.getDefaultMessage());
            });
        }
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId("1");

        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println(id);
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(UserQueryCondition condition,
                            @PageableDefault(page = 2, size = 17, sort = "username, asc") Pageable pageable) {
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }

    /**
     * 通过正则表达式控制URL格式
     *
     * 注解@JsonView指定视图，控制json输出内容
     *
     * 使用浏览器访问此接口，跳转到404.html/500.html
     * 使用REST工具访问此接口，被ControllerExceptionHandler处理，返回JSON
     */
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@ApiParam(value = "用户id") @PathVariable String id) {
//        throw new UserNotExistException(id);
        System.out.println("进入getInfo服务");
        User user = new User();
        user.setUsername("pyl");

        return user;
    }

    /**
     * 获取登录用户认证信息
     */
    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return user;
    }
}
