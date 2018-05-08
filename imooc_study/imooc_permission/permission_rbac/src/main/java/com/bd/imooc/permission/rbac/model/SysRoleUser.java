package com.bd.imooc.permission.rbac.model;

import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SysRoleUser {
    private Integer id;

    private Integer roleId;

    private Integer userId;

    private String operator;

    private Date operateTime;

    private String operateIp;
}