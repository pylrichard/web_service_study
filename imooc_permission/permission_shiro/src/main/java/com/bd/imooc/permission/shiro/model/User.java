package com.bd.imooc.permission.shiro.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User {
    private Integer uid;

    private String userName;

    private String password;

    private Set<Role> roles = new HashSet<>();
}
