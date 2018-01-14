package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.cache.server.hystrix.command.FailureModeCommand;

/**
 * 见101-Hystrix的fail-fast与fail-silient两种最基础的容错模式
 */
public class FailureModeCommandTest {
    public static void main(String[] args) {
        try {
            FailureModeCommand failureModeCommand = new FailureModeCommand(true);
            System.out.println(failureModeCommand.execute());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
