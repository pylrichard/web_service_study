package com.bd.imooc.seckill.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecKillOrder {
    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;
}
