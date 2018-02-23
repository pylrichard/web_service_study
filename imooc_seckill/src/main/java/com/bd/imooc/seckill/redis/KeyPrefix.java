package com.bd.imooc.seckill.redis;

public interface KeyPrefix {
    int expireSeconds();

    String getPrefix();
}
