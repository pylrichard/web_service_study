package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.alibaba.fastjson.JSONArray;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.bd.roncoo.eshop.common.http.HttpClientUtils;
import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * 见100-基于request collapser请求合并技术进一步优化批量查询
 */
public class GetProductsInfoCollapser extends HystrixCollapser<List<ProductInfo>, ProductInfo, Long> {
    private static Logger logger = LoggerFactory.getLogger(GetProductsInfoCollapser.class);
    private Long productId;

    public GetProductsInfoCollapser(Long productId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("GetProductsInfoCollapser"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                        /*
                            控制一个batch中允许被合并的request最大数量，然后触发一个batch的执行
                            默认无限大，不依靠数量触发执行，依靠时间
                         */
                        .withMaxRequestsInBatch(100)
                        //控制一个batch创建之后，多长时间以后就自动触发batch的执行，默认是10毫秒
                        .withTimerDelayInMilliseconds(20)));
        this.productId = productId;
    }

    @Override
    public Long getRequestArgument() {
        return productId;
    }

    @Override
    protected HystrixCommand<List<ProductInfo>> createCommand(
            Collection<CollapsedRequest<ProductInfo, Long>> requests) {
        return new BatchCommand(requests);
    }

    @Override
    protected void mapResponseToRequests(List<ProductInfo> batchResponse,
                                         Collection<CollapsedRequest<ProductInfo, Long>> requests) {
        int count = 0;
        for (CollapsedRequest<ProductInfo, Long> request : requests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    @Override
    protected String getCacheKey() {
        return "product_info_" + productId;
    }

    private static class BatchCommand extends HystrixCommand<List<ProductInfo>> {
        private final Collection<CollapsedRequest<ProductInfo, Long>> requests;

        private BatchCommand(Collection<CollapsedRequest<ProductInfo, Long>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductService"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetProductsInfoCollapserBatchCommand")));
            this.requests = requests;
        }

        @Override
        protected List<ProductInfo> run() throws Exception {
            /*
                将一个batch内的商品id拼接在一起
             */
            StringBuilder paramsBuilder = new StringBuilder("");
            for (CollapsedRequest<ProductInfo, Long> request : requests) {
                paramsBuilder.append(request.getArgument()).append(",");
            }
            String params = paramsBuilder.toString();
            params = params.substring(0, params.length() - 1);
            logger.info("BatchCommand内params=" + params);
            //合并后发送一次网络请求到商品服务获取所有结果
            String request = "http://localhost:8082/getProductsInfo?productIds=" + params;
            String response = HttpClientUtils.sendGetRequest(request);
            List<ProductInfo> productsInfo = JSONArray.parseArray(response, ProductInfo.class);
            for (ProductInfo productInfo : productsInfo) {
                logger.info("BatchCommand内productInfo=" + productInfo);
            }

            return productsInfo;
        }
    }
}
