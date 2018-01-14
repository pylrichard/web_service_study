package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * 见102-为商品服务接口调用增加stubbed fallback降级机制
 * <p>
 * fail-fast，不进入fallback降级逻辑，在线程池的线程中触发异常
 * <p>
 * fail-silent(常用)，进入fallback降级逻辑
 * HystrixCommand返回null给Tomcat调用线程
 * HystrixObservableCommand返回Observable.empty()
 */
public class FailureModeCommand extends HystrixCommand<Boolean> {
    private boolean failure;

    public FailureModeCommand(boolean failure) {
        super(HystrixCommandGroupKey.Factory.asKey("FailureModeGroup"));
        this.failure = failure;
    }

    @Override
    protected Boolean run() throws Exception {
        if (failure) {
            throw new Exception();
        }

        return true;
    }

    @Override
    protected Boolean getFallback() {
        return false;
    }
}
