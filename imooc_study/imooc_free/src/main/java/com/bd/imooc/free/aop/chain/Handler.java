package com.bd.imooc.free.aop.chain;

public abstract class Handler {
    private Handler sucessor;

    public Handler getSucessor() {
        return sucessor;
    }

    public void setSucessor(Handler sucessor) {
        this.sucessor = sucessor;
    }

    public void execute(){
        handleProcess();
        if(sucessor != null){
            sucessor.execute();
        }
    }

    protected abstract void handleProcess();
}
