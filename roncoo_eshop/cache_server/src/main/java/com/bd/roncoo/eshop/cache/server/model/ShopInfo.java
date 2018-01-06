package com.bd.roncoo.eshop.cache.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 店铺信息
 */
@Getter
@Setter
@ToString
public class ShopInfo {
    private Long id;
    private String name;
    private Integer level;
    private Double goodCommentRate;
}
