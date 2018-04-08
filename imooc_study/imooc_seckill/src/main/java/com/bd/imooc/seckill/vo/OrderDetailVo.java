package com.bd.imooc.seckill.vo;

import com.bd.imooc.seckill.domain.OrderInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;
}
