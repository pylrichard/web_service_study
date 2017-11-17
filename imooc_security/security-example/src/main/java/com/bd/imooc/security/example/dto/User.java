package com.bd.imooc.security.example.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

public class User {
    /**
     * 使用接口声明多个视图
     */
    public interface UserSimpleView {}
    public interface UserDetailView extends UserSimpleView {}

    private String id;
    private String username;
    @NotBlank
    private String password;
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
