package com.bd.imooc.seckill.rabbitmq;

import com.bd.imooc.seckill.domain.SecKillUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecKillMessage {
    private SecKillUser user;
    private long goodsId;
}
