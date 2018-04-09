package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.cache.server.hystrix.command.GetProductInfoCommand;

public class MultiLevelFallbackTest {
    public static void main(String[] args) throws Exception {
        GetProductInfoCommand getProductInfoCommand1 = new GetProductInfoCommand(-1L);
        System.out.println(getProductInfoCommand1.execute());
        GetProductInfoCommand getProductInfoCommand2 = new GetProductInfoCommand(-2L);
        System.out.println(getProductInfoCommand2.execute());
    }
}
