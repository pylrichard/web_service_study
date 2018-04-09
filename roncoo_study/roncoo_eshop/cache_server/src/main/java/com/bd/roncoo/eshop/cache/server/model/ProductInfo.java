package com.bd.roncoo.eshop.cache.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductInfo {
    private Long id;
    private String name;
    private Double price;
    private String pictureList;
    private String specification;
    private String service;
    private String color;
    private String size;
    private Long shopId;
    private String modifiedTime;
    private Long cityId;
    private String cityName;
    private Long brandId;
    private String brandName;
}
