package com.bd.imooc.permission.rbac.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
//根据id判断是否相同
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class SysAcl extends SysBase {
    private String code;

    private String name;

    private Integer aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    private Integer seq;

    private String remark;
}