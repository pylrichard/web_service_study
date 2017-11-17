package com.bd.roncoo.book.shop.common.dto;

import io.swagger.annotations.ApiModelProperty;

public class BookCondition {
    private String name;
    @ApiModelProperty("类别id")
    private long categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
