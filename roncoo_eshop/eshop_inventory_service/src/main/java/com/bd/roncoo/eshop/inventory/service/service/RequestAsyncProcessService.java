package com.bd.roncoo.eshop.inventory.service.service;

import com.bd.roncoo.eshop.inventory.service.request.Request;

/**
 * 请求异步执行Service
 */
public interface RequestAsyncProcessService {
    void process(Request request);
}
