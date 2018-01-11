package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.common.http.HttpClientUtils;

public class RequestCollapseTest {
    public static void main(String[] args) throws Exception {
        HttpClientUtils.sendGetRequest("http://localhost:8081/getProductInfos?productIds=1,1,2,2,3,4");
    }
}
