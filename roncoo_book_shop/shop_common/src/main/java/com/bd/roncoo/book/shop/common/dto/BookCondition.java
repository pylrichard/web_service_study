package com.bd.roncoo.book.shop.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BookCondition implements Serializable {
    private String name;
    @ApiModelProperty("类别id")
    private long categoryId;
}
