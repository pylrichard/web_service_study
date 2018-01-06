package com.bd.roncoo.eshop.cache.server.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class GetProductInfoFromMySQLCommand extends HystrixCommand<ProductInfo> {
    private Long productId;

    public GetProductInfoFromMySQLCommand(Long productId) {
        super(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        //模拟从MySQL中获取数据
        String json = "{\"id\": " + productId + ", \"name\": \"iPhone手机\", \"price\": 5599, " +
                "\"pictureList\":\"a.jpg,b.jpg\", \"specification\": \"iPhone的规格\", " +
                "\"service\": \"iPhone的售后服务\", \"color\": \"红色,白色,黑色\", " +
                "\"size\": \"5.5\", \"modifiedTime\": \"2017-01-01 12:00:00\"}";

        return JSONObject.parseObject(json, ProductInfo.class);
    }
}
