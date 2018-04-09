package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.common.http.HttpClientUtils;

/**
 * 见100-基于request collapser请求合并技术进一步优化批量查询
 */
public class RequestCollapseTest {
    public static void main(String[] args) throws Exception {
        HttpClientUtils.sendGetRequest("http://localhost:8081/getProductsInfo?productIds=1,1,2,2,3,4");
    }
}
