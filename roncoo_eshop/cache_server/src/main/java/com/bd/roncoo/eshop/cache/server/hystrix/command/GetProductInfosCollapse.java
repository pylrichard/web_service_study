package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.alibaba.fastjson.JSONArray;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.bd.roncoo.eshop.common.http.HttpClientUtils;
import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class GetProductInfosCollapse extends HystrixCollapser<List<ProductInfo>, ProductInfo, Long> {
    private static Logger logger = LoggerFactory.getLogger(GetProductInfosCollapse.class);
    private Long productId;

    public GetProductInfosCollapse(Long productId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("GetProductInfosCollapse"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                        .withMaxRequestsInBatch(100)
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
        StringBuilder paramsBuilder = new StringBuilder("");
        for (CollapsedRequest<ProductInfo, Long> request : requests) {
            paramsBuilder.append(request.getArgument()).append(",");
        }
        String params = paramsBuilder.toString();
        params = params.substring(0, params.length() - 1);
        logger.info("createCommand方法执行，params=" + params);

        return new BatchCommand(requests);
    }

    @Override
    protected void mapResponseToRequests(
            List<ProductInfo> batchResponse,
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

    private static final class BatchCommand extends HystrixCommand<List<ProductInfo>> {
        public final Collection<CollapsedRequest<ProductInfo, Long>> requests;

        public BatchCommand(Collection<CollapsedRequest<ProductInfo, Long>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductService"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetProductInfosCollapserBatchCommand")));
            this.requests = requests;
        }

        @Override
        protected List<ProductInfo> run() throws Exception {
            //将一个批次内的商品id拼接在一起
            StringBuilder paramsBuilder = new StringBuilder("");
            for (CollapsedRequest<ProductInfo, Long> request : requests) {
                paramsBuilder.append(request.getArgument()).append(",");
            }
            String params = paramsBuilder.toString();
            params = params.substring(0, params.length() - 1);
            //将多个商品id合并在一个batch内，发送一次网络请求获取所有结果
            String url = "http://localhost:8082/getProductInfos?productIds=" + params;
            String response = HttpClientUtils.sendGetRequest(url);
            List<ProductInfo> productInfos = JSONArray.parseArray(response, ProductInfo.class);
            for (ProductInfo productInfo : productInfos) {
                logger.info("BatchCommand内部，productInfo=" + productInfo);
            }

            return productInfos;
        }
    }
}
