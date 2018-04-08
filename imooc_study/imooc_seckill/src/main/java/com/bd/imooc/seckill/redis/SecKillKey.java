package com.bd.imooc.seckill.redis;

public class SecKillKey extends BasePrefix {
    private SecKillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SecKillKey isGoodsOver = new SecKillKey(0, "go");
    public static SecKillKey getSecKillPath = new SecKillKey(60, "mp");
    public static SecKillKey getSecKillVerifyCode = new SecKillKey(300, "vc");
}
