package com.bd.imooc.permission.rbac.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SysRole extends SysBase {
    private String name;

    private Integer type;

    private Integer status;

    private String remark;
}