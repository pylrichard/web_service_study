package com.bd.imooc.free.aop.chain;

public abstract class ChainHandler {
    public void execute(Chain chain){
        handleProcess();
        chain.proceed();
    }

    //Handler处理逻辑
    protected abstract void handleProcess();
}
