package com.bd.roncoo.eshop.cache.server;

import com.roncoo.eshop.cache.ha.http.HttpClientUtils;

public class TimeoutTest {

    public static void main(String[] args) throws Exception {
        HttpClientUtils.sendGetRequest("http://localhost:8081/getProductInfo?productId=-3");
    }

}
