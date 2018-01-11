package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.cache.server.degrade.IsDegrade;
import com.bd.roncoo.eshop.cache.server.hystrix.command.GetProductInfoFacadeCommand;

public class ManualDegradeTest {
    public static void main(String[] args) throws Exception {
        GetProductInfoFacadeCommand getProductInfoFacadeCommand1 = new GetProductInfoFacadeCommand(1L);
        System.out.println(getProductInfoFacadeCommand1.execute());
        IsDegrade.setDegrade(true);
        GetProductInfoFacadeCommand getProductInfoFacadeCommand2 = new GetProductInfoFacadeCommand(1L);
        System.out.println(getProductInfoFacadeCommand2.execute());
    }
}
