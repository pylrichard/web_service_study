package com.bd.imooc.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartVo {
    private List<CartProductVo> cartProductVoList;
    /**
     * 购物车中商品总价
     */
    private BigDecimal cartTotalPrice;
    /**
     * 是否已经都勾选
     */
    private Boolean allChecked;
    private String imageHost;
}
