package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.bd.roncoo.eshop.cache.server.degrade.IsDegrade;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

/**
 * 见104-基于facade command开发商品服务接口的手动降级机制
 */
public class GetProductInfoFacadeCommand extends HystrixCommand<ProductInfo> {
    private Long productId;

    public GetProductInfoFacadeCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetProductInfoFacadeCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //使用信号量机制做限流和资源隔离，因为这个command不需要关心timeout问题
                        .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(15)));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        /*
            根据手动降级标识判断要执行的command
         */
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
