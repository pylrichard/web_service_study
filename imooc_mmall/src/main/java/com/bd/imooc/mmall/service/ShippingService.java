package com.bd.imooc.mmall.service;

import com.bd.imooc.mmall.common.ServerResponse;
import com.bd.imooc.mmall.pojo.Shipping;
import com.github.pagehelper.PageInfo;

public interface ShippingService {
    ServerResponse addShipping(Integer userId, Shipping shipping);

    ServerResponse<String> deleteShipping(Integer userId, Integer shippingId);

    ServerResponse updateShipping(Integer userId, Shipping shipping);

    ServerResponse<Shipping> selectShipping(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> getShippingList(Integer userId, int pageNum, int pageSize);
}
