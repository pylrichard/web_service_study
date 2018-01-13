package com.bd.roncoo.eshop.cache.server;

import com.bd.roncoo.eshop.common.http.HttpClientUtils;

public class TimeoutTest {
    public static void main(String[] args) throws Exception {
        String request = "http://localhost:8081/getProductInfo?productId=";
        HttpClientUtils.sendGetRequest(request + "-2");
    }
}
