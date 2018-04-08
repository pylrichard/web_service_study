package com.bd.imooc.permission.rbac.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SysDept extends SysBase {
    private String name;

    private Integer parentId;

    private String level;

    private Integer seq;

    private String remark;
}