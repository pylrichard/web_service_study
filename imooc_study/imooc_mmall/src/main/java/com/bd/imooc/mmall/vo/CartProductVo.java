package com.bd.imooc.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 购物车中同类商品对象
 */
@Getter
@Setter
public class CartProductVo {
    private Integer id;
    private Integer userId;
    private Integer productId;
    /**
     * 购物车中此类商品的数量
     */
    private Integer quantity;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    /**
     * 商品库存
     */
    private Integer productStock;
    private Integer productChecked;
    /**
     * 限制商品数量的返回结果
     */
    private String limitQuantity;
}
