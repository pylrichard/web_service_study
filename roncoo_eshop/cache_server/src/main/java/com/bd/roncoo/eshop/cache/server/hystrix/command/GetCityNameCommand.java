package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.bd.roncoo.eshop.cache.server.cache.local.LocationCache;
import com.netflix.hystrix.*;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

/**
 * 获取城市名称的command
 *
 * 线程池隔离适合绝大多数的场景
 * 信号量隔离适合对内部复杂业务逻辑的访问，不涉及网络请求，不需要处理超时
 * 见91-基于Hystrix的信号量技术对地理位置获取逻辑进行资源隔离与限流
 */
public class GetCityNameCommand extends HystrixCommand<String> {
    private Long cityId;

    public GetCityNameCommand(Long cityId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetCityNameGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetCityNameCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetCityNamePool"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //信号量隔离
                        .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)
                        //并发量，默认是10，超过阈值，之后的command被reject
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(15)));
        this.cityId = cityId;
    }

    @Override
    protected String run() throws Exception {
        return LocationCache.getCityName(cityId);
    }
}
