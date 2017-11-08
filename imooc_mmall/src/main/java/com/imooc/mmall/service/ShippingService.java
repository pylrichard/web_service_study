package com.imooc.mmall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mmall.common.ServerResponse;
import com.imooc.mmall.pojo.Shipping;

public interface ShippingService {
    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse<String> del(Integer userId, Integer shippingId);
    ServerResponse update(Integer userId, Shipping shipping);
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
