package com.bd.imooc.seckill.redis;

public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getSecKillOrderByUidGid = new OrderKey("moug");
}
