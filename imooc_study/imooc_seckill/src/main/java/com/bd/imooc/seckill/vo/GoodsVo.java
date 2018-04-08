package com.bd.imooc.seckill.vo;

import com.bd.imooc.seckill.domain.Goods;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GoodsVo extends Goods {
    private Double secKillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
