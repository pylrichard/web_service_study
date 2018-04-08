package com.bd.imooc.permission.rbac.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchLogParam {
    /**
     * 对应LogType
     */
    private Integer type;

    private String beforeSeg;

    private String afterSeg;

    private String operator;
    /**
     * 格式为yyyy-MM-dd HH:mm:ss
     */
    private String fromTime;

    private String toTime;
}
