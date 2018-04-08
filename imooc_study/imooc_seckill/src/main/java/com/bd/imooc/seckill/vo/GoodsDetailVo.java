package com.bd.imooc.seckill.vo;

import com.bd.imooc.seckill.domain.SecKillUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsDetailVo {
    private int secKillStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods;
    private SecKillUser user;
}
