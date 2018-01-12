package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.bd.roncoo.eshop.cache.server.degrade.IsDegrade;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

/**
 * 封装多个command
 */
public class GetProductInfoFacadeCommand extends HystrixCommand<ProductInfo> {
    private Long productId;

    public GetProductInfoFacadeCommand(Long productId) {
        /*
            默认情况下
            通过command group定义一个线程池
            通过command group聚合监控和报警信息
            同一个command group中的请求会进入同一个线程池
         */
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetProductInfoFacadeCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(15)));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        if (!IsDegrade.isDegrade()) {
            return new GetProductInfoCommand(productId).execute();
        } else {
            return new GetProductInfoFromDbCommand(productId).execute();
        }
    }

    @Override
    protected ProductInfo getFallback() {
        return new ProductInfo();
    }
}
