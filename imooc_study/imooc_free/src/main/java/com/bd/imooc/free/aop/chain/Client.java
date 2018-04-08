package com.bd.imooc.free.aop.chain;

public class Client {
    static class HandlerA extends Handler{
        @Override
        protected void handleProcess() {
            System.out.println("handle by a");
        }
    }
    static class HandlerB extends Handler{
        @Override
        protected void handleProcess() {
            System.out.println("handle by b");
        }
    }
    static class HandlerC extends Handler{
        @Override
        protected void handleProcess() {
            System.out.println("handle by c");
        }
    }

    public static void main(String[] args){
        Handler handlerA = new HandlerA();
        Handler handlerB = new HandlerB();
        Handler handlerC = new HandlerC();

        /*
            此方法的缺点是要设置链式依赖关系
         */
        handlerA.setSucessor(handlerB);
        handlerB.setSucessor(handlerC);

        //实现链式调用
        handlerA.execute();
    }
}
