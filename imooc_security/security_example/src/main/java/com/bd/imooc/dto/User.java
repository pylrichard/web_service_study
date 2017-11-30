package com.bd.imooc.dto;

import com.bd.imooc.validator.ExampleConstraint;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

public class User {
    /**
     * 使用接口声明多个视图
     */
    public interface UserSimpleView {}
    public interface UserDetailView extends UserSimpleView {}

    private String id;
    @ExampleConstraint(message = "自定义验证参数注解")
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 需要在方法参数添加注解@Valid才能生效
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    @Past(message = "生日必须是过去的时间")
    private Date birthday;

    /**
     * 值对象get()指定视图
     */
    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 也会显示UserSimpleView标注的成员变量
     */
    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
