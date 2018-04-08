package com.bd.imooc.permission.rbac.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SysBase {
    private Integer id;

    private String operator;

    private Date operateTime;

    private String operateIp;
}
