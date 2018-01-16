package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.common.http.HttpClientUtils;

/**
 * 见108-Hystrix dashboard可视化分布式系统监控环境部署
 */
public class HystrixDashboardTest {
    public static void main(String[] args) {
        HttpClientUtils.sendGetRequest("http://localhost:8081/getProductInfo?productId=1");
    }
}
