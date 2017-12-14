package com.bd.imooc.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderVo {
    private Long orderNo;
    private BigDecimal payment;
    private Integer paymentType;
    private String paymentTypeDesc;
    private Integer postage;
    private Integer status;
    private String statusDesc;
    private String paymentTime;
    private String sendTime;
    private String endTime;
    private String closeTime;
    private String createTime;
    /**
     * 订单明细集合
     */
    private List<OrderItemVo> orderItemVoList;
    private String imageHost;
    private Integer shippingId;
    private String receiverName;
    private ShippingVo shippingVo;
}
