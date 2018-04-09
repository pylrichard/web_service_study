package com.bd.roncoo.book.shop.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorCondition {
    private String name;
    /**
     * 构建Domain使用int原始类型，非空字段，默认为0
     * 构建查询条件使用Integer封装类型，Integer可以为null，表示查询条件中没有此条件
     * 使用int原始类型，getAge()默认为0表示查询age=0的Author，结果有歧义
     */
    private Integer age;
    private Integer ageTo;
    private String email;
    private Sex sex;
}
