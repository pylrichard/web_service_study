package com.bd.imooc.permission.rbac.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SearchLogDto {
    private Integer type;

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private Date fromTime;

    private Date toTime;
}
