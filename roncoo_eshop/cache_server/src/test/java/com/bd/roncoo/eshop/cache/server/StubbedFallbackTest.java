package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.cache.server.hystrix.command.GetProductInfoCommand;

/**
 * 见102-为商品服务接口调用增加stubbed fallback降级机制
 */
public class StubbedFallbackTest {
    public static void main(String[] args) {
        GetProductInfoCommand getProductInfoCommand = new GetProductInfoCommand(-1L);
        System.out.println(getProductInfoCommand.execute());
    }
}
